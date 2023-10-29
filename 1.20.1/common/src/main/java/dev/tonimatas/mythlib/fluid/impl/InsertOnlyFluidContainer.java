package dev.tonimatas.mythlib.fluid.impl;

import net.minecraft.util.Mth;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.util.FluidUtils;

import java.util.function.BiPredicate;
import java.util.function.IntToLongFunction;

public class InsertOnlyFluidContainer extends SimpleFluidContainer {
    public InsertOnlyFluidContainer(IntToLongFunction maxAmount, int tanks, BiPredicate<Integer, FluidHolder> fluidFilter) {
        super(maxAmount, tanks, fluidFilter);
    }

    @Override
    public FluidHolder extractFluid(FluidHolder fluid, boolean simulate) {
        return FluidUtils.emptyFluid();
    }

    @Override
    public FluidHolder internalExtract(FluidHolder fluid, boolean simulate) {
        for (int i = 0; i < this.storedFluid.size(); i++) {
            if (fluidFilter.test(i, fluid)) {
                FluidHolder toExtract = fluid.copyHolder();
                if (storedFluid.isEmpty()) {
                    return FluidUtils.emptyFluid();
                } else if (storedFluid.get(i).matches(fluid)) {
                    long extractedAmount = (long) Mth.clamp(fluid.getFluidAmount(), 0, storedFluid.get(i).getFluidAmount());
                    toExtract.setAmount(extractedAmount);
                    if (simulate) return toExtract;
                    this.storedFluid.get(i).setAmount(storedFluid.get(i).getFluidAmount() - extractedAmount);
                    if (storedFluid.get(i).getFluidAmount() == 0) storedFluid.set(i, FluidUtils.emptyFluid());
                    return toExtract;
                }
            }
        }
        return FluidUtils.emptyFluid();
    }

    @Override
    public boolean allowsExtraction() {
        return false;
    }
}
