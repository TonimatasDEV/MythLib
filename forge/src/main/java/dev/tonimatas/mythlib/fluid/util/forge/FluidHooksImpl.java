package dev.tonimatas.mythlib.fluid.util.forge;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidType;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.PlatformFluidHandler;
import dev.tonimatas.mythlib.fluid.base.forge.ForgeFluidHandler;
import dev.tonimatas.mythlib.fluid.base.forge.ForgeFluidHolder;
import org.jetbrains.annotations.Nullable;

public class FluidHooksImpl {
    public static FluidHolder newFluidHolder(Fluid fluid, long amount, CompoundTag tag) {
        return new ForgeFluidHolder(fluid, (int) amount, tag);
    }

    public static FluidHolder fluidFromCompound(CompoundTag compoundTag) {
        return ForgeFluidHolder.fromCompound(compoundTag);
    }

    public static PlatformFluidHandler getItemFluidManager(ItemStack stack) {
        return new ForgeFluidHandler(stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElseThrow(IllegalArgumentException::new));
    }

    public static PlatformFluidHandler getBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        return new ForgeFluidHandler(entity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).orElseThrow(IllegalArgumentException::new));
    }

    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        return entity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).isPresent();
    }

    public static boolean isFluidContainingItem(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
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
