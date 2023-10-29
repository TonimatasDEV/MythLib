package dev.tonimatas.mythlib.networking;

import net.minecraft.network.FriendlyByteBuf;

public interface PacketHandler<T extends Packet<T>> {
    void encode(T packet, FriendlyByteBuf buf);

    T decode(FriendlyByteBuf buf);

    PacketContext handle(T packet);
}
