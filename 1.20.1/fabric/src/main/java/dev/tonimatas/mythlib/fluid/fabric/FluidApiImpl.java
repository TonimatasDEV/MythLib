package dev.tonimatas.mythlib.fluid.fabric;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.fluid.base.FluidContainer;
import dev.tonimatas.mythlib.fluid.base.ItemFluidContainer;
import dev.tonimatas.mythlib.fluid.base.fabric.PlatformFluidContainer;
import dev.tonimatas.mythlib.fluid.base.fabric.PlatformFluidItemHandler;
import dev.tonimatas.mythlib.item.ItemStackHolder;
import dev.tonimatas.mythlib.item.ItemStackStorage;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ALL")
public class FluidApiImpl {
    public static ItemFluidContainer getItemFluidContainer(ItemStackHolder stack) {
        return new PlatformFluidItemHandler(stack);
    }

    public static FluidContainer getBlockFluidContainer(BlockEntity entity, @Nullable Direction direction) {
        return new PlatformFluidContainer(FluidStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), direction));
    }

    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        return FluidStorage.SIDED.find(entity.getLevel(), entity.getBlockPos(), direction) != null;
    }

    public static boolean isFluidContainingItem(ItemStack stack) {
        return FluidStorage.ITEM.find(stack, ItemStackStorage.of(stack)) != null;
    }
}
