package dev.tonimatas.mythlib.networking;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.NotImplementedException;

import java.util.function.BooleanSupplier;

public class PacketChannelHelper {
    @ExpectPlatform
    public static void registerChannel(ResourceLocation channel, int protocolVersion) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends Packet<T>> void registerS2CPacket(ResourceLocation channel, ResourceLocation id, PacketHandler<T> packet, Class<T> packetClass) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends Packet<T>> void registerC2SPacket(ResourceLocation channel, ResourceLocation id, PacketHandler<T> handler, Class<T> packetClass) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends Packet<T>> void sendToServer(ResourceLocation channel, T packet) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static <T extends Packet<T>> void sendToPlayer(ResourceLocation channel, T packet, Player player) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static boolean canSendPlayerPackets(ResourceLocation channel, Player player) {
        throw new NotImplementedException();
    }
}
