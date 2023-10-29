package dev.tonimatas.mythlib.fluid.base.forge;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import dev.tonimatas.mythlib.AutoSerializable;
import dev.tonimatas.mythlib.fluid.base.FluidContainer;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.util.Serializable;
import dev.tonimatas.mythlib.util.Updatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ForgeFluidContainer<T extends FluidContainer & Updatable<BlockEntity>>(T container, BlockEntity entity) implements IFluidHandler, ICapabilityProvider, AutoSerializable {

    @Override
    public int getTanks() {
        return container.getSize();
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int i) {
        return new ForgeFluidHolder(container.getFluids().get(i)).fluidStack;
    }

    @Override
    public int getTankCapacity(int i) {
        return (int) this.container.getTankCapacity(i);
    }

    @Override
    public boolean isFluidValid(int i, @NotNull FluidStack fluidStack) {
        return this.container.getFluids().get(i).matches(new ForgeFluidHolder(fluidStack));
    }

    @Override
    public int fill(FluidStack fluidStack, FluidAction fluidAction) {
        int i = (int) this.container.insertFluid(new ForgeFluidHolder(fluidStack), fluidAction.simulate());
        if (i > 0 && fluidAction.execute()) this.container.update(entity);
        return i;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        FluidStack fluidStack1 = new ForgeFluidHolder(this.container.extractFluid(new ForgeFluidHolder(fluidStack), fluidAction.simulate())).getFluidStack();
        if(!fluidStack1.isEmpty() && fluidAction.execute()) this.container.update(entity);
        return fluidStack1;
    }

    @Override
    public @NotNull FluidStack drain(int i, FluidAction fluidAction) {
        FluidHolder fluid = this.container.getFluids().get(0).copyHolder();
        if (fluid.isEmpty()) return FluidStack.EMPTY;
        fluid.setAmount(i);
        FluidStack fluidStack = new ForgeFluidHolder(this.container.extractFluid(fluid, fluidAction.simulate())).getFluidStack();
        if(!fluidStack.isEmpty() && fluidAction.execute()) this.container.update(entity);
        return fluidStack;
    }

    @Override
    public @NotNull <Y> LazyOptional<Y> getCapability(@NotNull Capability<Y> capability, @Nullable Direction arg) {
        return capability == ForgeCapabilities.FLUID_HANDLER && container.getContainer(arg) != null ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
    }

    @Override
    public Serializable getSerializable() {
        return container;
    }
}
