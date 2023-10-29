package dev.tonimatas.mythlib.energy.base;

import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.util.Updatable;

public interface MythEnergyBlock<T extends EnergyContainer & Updatable<BlockEntity>> {
    T getEnergyStorage();
}
