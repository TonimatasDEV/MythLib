package dev.tonimatas.mythlib.fluid.base;

import java.util.List;

public interface PlatformFluidHandler {
    long insertFluid(FluidHolder fluid, boolean simulate);

    FluidHolder extractFluid(FluidHolder fluid, boolean simulate);

    int getTankAmount();

    FluidHolder getFluidInTank(int tank);

    List<FluidHolder> getFluidTanks();

    long getTankCapacity(int tank);

    boolean supportsInsertion();

    boolean supportsExtraction();
}
