package dev.tonimatas.mythlib.fluid.util.fabric;

import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.fabric.FabricFluidHolder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("UnstableApiUsage")
public class FluidUtilsImpl {
    public static FluidHolder newFluidHolder(Fluid fluid, long amount, CompoundTag tag) {
        return FabricFluidHolder.of(fluid, tag, amount);
    }

    public static FluidHolder fluidFromCompound(CompoundTag compoundTag) {
        FabricFluidHolder fluid = FabricFluidHolder.of(null, 0);
        fluid.deserialize(compoundTag);
        return fluid;
    }

    public static FluidHolder emptyFluid() {
        return FabricFluidHolder.empty();
    }

    public static long toMillibuckets(long amount) {
        return amount / 81;
    }

    public static long getBucketAmount() {
        return FluidConstants.BUCKET;
    }

    public static long getBottleAmount() {
        return FluidConstants.BOTTLE;
    }

    public static long getBlockAmount() {
        return FluidConstants.BLOCK;
    }

    public static long getIngotAmount() {
        return FluidConstants.INGOT;
    }

    public static long getNuggetAmount() {
        return FluidConstants.NUGGET;
    }
}
