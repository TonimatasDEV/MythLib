package dev.tonimatas.mythlib.energy.base;

import net.minecraft.core.Direction;
import net.minecraft.world.Clearable;
import dev.tonimatas.mythlib.util.Serializable;

public interface EnergyContainer extends Serializable, Clearable {
    default EnergyContainer getContainer(Direction direction) {
        return this;
    }

    long insertEnergy(long maxAmount, boolean simulate);

    default long internalInsert(long amount, boolean simulate) {
        return insertEnergy(amount, simulate);
    }

    long extractEnergy(long maxAmount, boolean simulate);

    default long internalExtract(long amount, boolean simulate) {
        return extractEnergy(amount, simulate);
    }

    void setEnergy(long energy);

    long getStoredEnergy();

    long getMaxCapacity();

    long maxInsert();

    long maxExtract();

    boolean allowsInsertion();

    boolean allowsExtraction();

    EnergySnapshot createSnapshot();

    default void readSnapshot(EnergySnapshot snapshot) {
        snapshot.loadSnapshot(this);
    }
}
