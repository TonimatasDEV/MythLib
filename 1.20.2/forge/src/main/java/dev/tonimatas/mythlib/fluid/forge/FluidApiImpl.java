package dev.tonimatas.mythlib.fluid.forge;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import dev.tonimatas.mythlib.fluid.base.FluidContainer;
import dev.tonimatas.mythlib.fluid.base.ItemFluidContainer;
import dev.tonimatas.mythlib.fluid.base.forge.PlatformBlockFluidHandler;
import dev.tonimatas.mythlib.fluid.base.forge.PlatformFluidItemHandler;
import dev.tonimatas.mythlib.item.ItemStackHolder;
import org.jetbrains.annotations.Nullable;

public class FluidApiImpl {
    public static ItemFluidContainer getItemFluidContainer(ItemStackHolder stack) {
        return isFluidContainingItem(stack.getStack()) ? new PlatformFluidItemHandler(stack) : null;
    }

    public static FluidContainer getBlockFluidContainer(BlockEntity entity, @Nullable Direction direction) {
        return isFluidContainingBlock(entity, direction) ? new PlatformBlockFluidHandler(entity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).orElseThrow(IllegalArgumentException::new)) : null;
    }

    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        return entity.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).isPresent();
    }

    public static boolean isFluidContainingItem(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
    }
}
