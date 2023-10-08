package dev.tonimatas.mythlib.fluid.base;

import net.minecraft.core.Direction;
import net.minecraft.world.Clearable;
import dev.tonimatas.mythlib.util.Serializable;

import java.util.List;

public interface FluidContainer extends Serializable, Clearable {
    long insertFluid(FluidHolder fluid, boolean simulate);

    default long internalInsert(FluidHolder fluids, boolean simulate) {
        return insertFluid(fluids, simulate);
    }

    FluidHolder extractFluid(FluidHolder fluid, boolean simulate);

    default FluidHolder internalExtract(FluidHolder fluid, boolean simulate) {
        return extractFluid(fluid, simulate);
    }

    void setFluid(int slot, FluidHolder fluid);

    List<FluidHolder> getFluids();

    int getSize();

    boolean isEmpty();

    FluidContainer copy();

    long getTankCapacity(int tankSlot);

    void fromContainer(FluidContainer container);

    long extractFromSlot(FluidHolder fluidHolder, FluidHolder toInsert, Runnable snapshot);

    boolean allowsInsertion();

    boolean allowsExtraction();

    default FluidContainer getContainer(Direction direction) {
        return this;
    }

    FluidSnapshot createSnapshot();

    default void readSnapshot(FluidSnapshot snapshot) {
        snapshot.loadSnapshot(this);
    }
}
