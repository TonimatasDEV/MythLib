package dev.tonimatas.mythlib.networking.forge;

import dev.tonimatas.mythlib.networking.Packet;
import dev.tonimatas.mythlib.networking.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import java.util.HashMap;
import java.util.Map;

public class PacketChannelHelperImpl {
    public static final Map<ResourceLocation, SimpleChannel> CHANNELS = new HashMap<>();

    public static void registerChannel(ResourceLocation name, int protocolVersion) {
        CHANNELS.put(name, ChannelBuilder.named(name).networkProtocolVersion(protocolVersion).simpleChannel());
    }

    public static <T extends Packet<T>> void registerS2CPacket(ResourceLocation name, ResourceLocation id, PacketHandler<T> handler, Class<T> packetClass) {
        SimpleChannel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        channel.messageBuilder(packetClass, NetworkDirection.PLAY_TO_CLIENT).encoder(handler::encode).decoder(handler::decode).consumerNetworkThread((msg, ctx) -> {
            Player player = ctx.getSender();
            if (player != null) {
                ctx.enqueueWork(() -> handler.handle(msg).apply(player, player.level()));
            }
            ctx.setPacketHandled(true);
        }).add();
    }

    public static <T extends Packet<T>> void registerC2SPacket(ResourceLocation name, ResourceLocation id, PacketHandler<T> handler, Class<T> packetClass) {
        SimpleChannel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        channel.messageBuilder(packetClass, NetworkDirection.PLAY_TO_SERVER).encoder(handler::encode).decoder(handler::decode).consumerNetworkThread((msg, ctx) -> {
            Player player = ctx.getSender();
            if (player != null) {
                ctx.enqueueWork(() -> handler.handle(msg).apply(player, player.level()));
            }
            ctx.setPacketHandled(true);
        }).add();
    }

    @OnlyIn(Dist.CLIENT)
    private static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static <T extends Packet<T>> void sendToServer(ResourceLocation name, T packet) {
        SimpleChannel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        channel.send(packet, PacketDistributor.SERVER.noArg());
    }

    public static <T extends Packet<T>> void sendToPlayer(ResourceLocation name, T packet, Player player) {
        SimpleChannel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        if (player instanceof ServerPlayer serverPlayer) {
            channel.send(packet, PacketDistributor.PLAYER.with(serverPlayer));
        }
    }

    public static boolean canSendPlayerPackets(ResourceLocation name, Player player) {
        SimpleChannel channel = CHANNELS.get(name);
        if (channel != null && player instanceof ServerPlayer serverPlayer) {
            return channel.isRemotePresent(serverPlayer.connection.getConnection());
        }
        return false;
    }
}