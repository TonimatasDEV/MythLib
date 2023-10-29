package dev.tonimatas.mythlib.fluid.base;

import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.util.Updatable;

public interface MythFluidBlock<T extends FluidContainer & Updatable<BlockEntity>> {
    T getFluidContainer();
}