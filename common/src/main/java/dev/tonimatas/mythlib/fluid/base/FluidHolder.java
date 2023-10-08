package dev.tonimatas.mythlib.fluid.base;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import dev.tonimatas.mythlib.fluid.util.FluidHooks;

import java.util.function.Predicate;

public interface FluidHolder {
    static FluidHolder of(Fluid fluid) {
        return FluidHooks.newFluidHolder(fluid, FluidHooks.buckets(1D), null);
    }

    static FluidHolder of(Fluid fluid, double buckets, CompoundTag tag) {
        return FluidHooks.newFluidHolder(fluid, FluidHooks.buckets(buckets), tag);
    }

    Fluid getFluid();

    void setFluid(Fluid fluid);

    long getFluidAmount();

    void setAmount(long amount);

    CompoundTag getCompound();

    void setCompound(CompoundTag tag);

    boolean isEmpty();

    boolean matches(FluidHolder fluidHolder);

    FluidHolder copyHolder();

    CompoundTag serialize();

    void deserialize(CompoundTag tag);

    default FluidHolder copyWithAmount(long amount) {
        FluidHolder copy = copyHolder();
        if (!copy.isEmpty()) copy.setAmount(amount);
        return copy;
    }

    @SuppressWarnings("deprecation")
    default Holder<Fluid> getFluidHolder() {
        return getFluid().builtInRegistryHolder();
    }

    @SuppressWarnings("deprecation")
    default boolean is(TagKey<Fluid> tagKey) {
        return getFluid().is(tagKey);
    }

    default boolean is(Fluid fluid) {
        return getFluid() == fluid;
    }

    default boolean is(Predicate<Holder<Fluid>> predicate) {
        return predicate.test(getFluidHolder());
    }

    default boolean is(Holder<Fluid> fluid) {
        return getFluidHolder() == fluid;
    }

}
