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
import net.minecraftforge.network.SimpleChannel;

import java.util.HashMap;
import java.util.Map;

public class PacketChannelHelperImpl {
    public static final Map<ResourceLocation, Channel> CHANNELS = new HashMap<>();

    public static void registerChannel(ResourceLocation name, int protocolVersion) {
        Channel channel = new Channel(0, ChannelBuilder.named(name).networkProtocolVersion(protocolVersion).simpleChannel());
        CHANNELS.put(name, channel);
    }

    public static <T extends Packet<T>> void registerS2CPacket(ResourceLocation name, ResourceLocation id, PacketHandler<T> handler, Class<T> packetClass) {
        Channel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        //channel.channel.registerMessage(++channel.packets, packetClass, handler::encode, handler::decode, (msg, ctx) -> { // TODO: Port
        //    NetworkEvent.Context context = ctx.get();
        //    Player player = context.getSender() == null ? getPlayer() : null;
        //    if (player != null) {
        //        context.enqueueWork(() -> handler.handle(msg).apply(player, player.level()));
        //    }
        //    context.setPacketHandled(true);
        //});
    }

    public static <T extends Packet<T>> void registerC2SPacket(ResourceLocation name, ResourceLocation id, PacketHandler<T> handler, Class<T> packetClass) {
        Channel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        //channel.channel.registerMessage(++channel.packets, packetClass, handler::encode, handler::decode, (msg, ctx) -> { // TODO: Port
        //    NetworkEvent.Context context = ctx.get();
        //    Player player = context.getSender();
        //    if (player != null) {
        //        context.enqueueWork(() -> handler.handle(msg).apply(player, player.level()));
        //    }
        //    context.setPacketHandled(true);
        //});
    }

    @OnlyIn(Dist.CLIENT)
    private static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static <T extends Packet<T>> void sendToServer(ResourceLocation name, T packet) {
        Channel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        //channel.channel.sendToServer(packet); // TODO: Port
    }

    public static <T extends Packet<T>> void sendToPlayer(ResourceLocation name, T packet, Player player) {
        Channel channel = CHANNELS.get(name);
        if (channel == null) {
            throw new IllegalStateException("Channel " + name + " not registered");
        }
        if (player instanceof ServerPlayer serverPlayer) {
            //channel.channel.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet); // TODO: Port
        }
    }

    public static boolean canSendPlayerPackets(ResourceLocation name, Player player) {
        Channel channel = CHANNELS.get(name);
        if (channel != null && player instanceof ServerPlayer serverPlayer) {
            return channel.channel.isRemotePresent(serverPlayer.connection.getConnection());
        }
        return false;
    }

    private static final class Channel {
        private int packets;
        private final SimpleChannel channel;

        private Channel(int packets, SimpleChannel channel) {
            this.packets = packets;
            this.channel = channel;
        }
    }
}