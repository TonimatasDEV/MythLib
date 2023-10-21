package dev.tonimatas.mythlib.fluid.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.Fluid;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class FluidUtils {
    @ExpectPlatform
    public static FluidHolder newFluidHolder(Fluid fluid, long amount, @Nullable CompoundTag tag) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static FluidHolder fluidFromCompound(CompoundTag compoundTag) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static FluidHolder emptyFluid() {
        throw new NotImplementedException();
    }

    public static long buckets(double buckets) {
        return (long) (buckets * getBucketAmount());
    }

    @ExpectPlatform
    public static long toMillibuckets(long amount) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getBucketAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getBottleAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getBlockAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getIngotAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getNuggetAmount() {
        throw new NotImplementedException();
    }

    public static void writeToBuffer(FluidHolder holder, FriendlyByteBuf buffer) {
        if (holder.isEmpty()) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeVarInt(BuiltInRegistries.FLUID.getId(holder.getFluid()));
            buffer.writeVarLong(holder.getFluidAmount());
            buffer.writeNbt(holder.getCompound());
        }
    }

    public static FluidHolder readFromBuffer(FriendlyByteBuf buffer) {
        if (!buffer.readBoolean()) return FluidUtils.emptyFluid();
        Fluid fluid = BuiltInRegistries.FLUID.byId(buffer.readVarInt());
        long amount = buffer.readVarLong();
        return FluidHolder.of(fluid, amount, buffer.readNbt());
    }
}
