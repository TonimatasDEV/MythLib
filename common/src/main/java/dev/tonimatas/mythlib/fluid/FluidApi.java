package dev.tonimatas.mythlib.fluid;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.fluid.base.FluidContainer;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.ItemFluidContainer;
import dev.tonimatas.mythlib.fluid.util.FluidHooks;
import org.apache.commons.lang3.NotImplementedException;

@SuppressWarnings({"BooleanMethodIsAlwaysInverted", "unused"})
public class FluidApi {
    @ExpectPlatform
    public static ItemFluidContainer getItemFluidContainer(ItemStack stack) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static FluidContainer getBlockFluidContainer(BlockEntity entity, Direction direction) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static boolean isFluidContainingBlock(BlockEntity entity, Direction direction) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static boolean isFluidContainingItem(ItemStack stack) {
        throw new NotImplementedException();
    }

    public static long moveFluid(FluidContainer from, FluidContainer to, FluidHolder amount, boolean simulate) {
        FluidHolder extracted = from.extractFluid(amount, true);
        long inserted = to.insertFluid(extracted, true);
        FluidHolder toInsert = FluidHooks.newFluidHolder(amount.getFluid(), inserted, amount.getCompound());
        FluidHolder simulatedExtraction = from.extractFluid(toInsert, true);
        if (!simulate && inserted > 0 && simulatedExtraction.getFluidAmount() == inserted) {
            from.extractFluid(toInsert, false);
            to.insertFluid(toInsert, false);
        }
        return Math.max(0, inserted);
    }

    public static long moveFluid(ItemStack from, ItemStack to, FluidHolder fluidHolder, boolean simulate) {
        if (!isFluidContainingItem(from) || !isFluidContainingItem(to)) return 0;
        FluidContainer fromFluid = getItemFluidContainer(from);
        FluidContainer toFluid = getItemFluidContainer(to);
        return moveFluid(fromFluid, toFluid, fluidHolder, simulate);
    }

    public static long moveFluid(BlockEntity from, BlockEntity to, FluidHolder fluidHolder, boolean simulate) {
        if (!isFluidContainingBlock(from, null) || !isFluidContainingBlock(to, null)) return 0;
        FluidContainer fromFluid = getBlockFluidContainer(from, null);
        FluidContainer toFluid = getBlockFluidContainer(to, null);
        return moveFluid(fromFluid, toFluid, fluidHolder, simulate);
    }

    public static long moveFluid(BlockEntity from, Direction direction, ItemStack to, FluidHolder fluidHolder, boolean simulate) {
        if (!isFluidContainingBlock(from, direction) || !isFluidContainingItem(to)) return 0;
        FluidContainer fromFluid = getBlockFluidContainer(from, direction);
        FluidContainer toFluid = getItemFluidContainer(to);
        return moveFluid(fromFluid, toFluid, fluidHolder, simulate);
    }

    public static long moveFluid(ItemStack from, BlockEntity to, Direction direction, FluidHolder fluidHolder, boolean simulate) {
        if (!isFluidContainingItem(from) || !isFluidContainingBlock(to, direction)) return 0;
        FluidContainer fromFluid = getItemFluidContainer(from);
        FluidContainer toFluid = getBlockFluidContainer(to, direction);
        return moveFluid(fromFluid, toFluid, fluidHolder, simulate);
    }
}
