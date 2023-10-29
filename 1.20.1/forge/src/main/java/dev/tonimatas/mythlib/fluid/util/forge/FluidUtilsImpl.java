package dev.tonimatas.mythlib.fluid.util.forge;

import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.forge.ForgeFluidHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;

public class FluidUtilsImpl {
    public static FluidHolder newFluidHolder(Fluid fluid, long amount, CompoundTag tag) {
        return new ForgeFluidHolder(fluid, (int) amount, tag);
    }

    public static FluidHolder fluidFromCompound(CompoundTag compoundTag) {
        return ForgeFluidHolder.fromCompound(compoundTag);
    }

    public static FluidHolder emptyFluid() {
        return ForgeFluidHolder.empty();
    }


    public static long toMillibuckets(long amount) {
        return amount;
    }


    public static long getBucketAmount() {
        return FluidType.BUCKET_VOLUME;
    }


    public static long getBottleAmount() {
        return FluidType.BUCKET_VOLUME / 4;
    }


    public static long getBlockAmount() {
        return FluidType.BUCKET_VOLUME;
    }

    public static long getIngotAmount() {
        return 90;
    }

    public static long getNuggetAmount() {
        return 10;
    }
}
