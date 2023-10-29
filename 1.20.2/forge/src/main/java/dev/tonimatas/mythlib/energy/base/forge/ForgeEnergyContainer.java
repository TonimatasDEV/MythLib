package dev.tonimatas.mythlib.energy.base.forge;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import dev.tonimatas.mythlib.AutoSerializable;
import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.util.Serializable;
import dev.tonimatas.mythlib.util.Updatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record ForgeEnergyContainer<T extends EnergyContainer & Updatable<BlockEntity>>(T container, BlockEntity entity) implements IEnergyStorage, AutoSerializable, ICapabilityProvider {
    @Override
    @NotNull
    public <Y> LazyOptional<Y> getCapability(@NotNull Capability<Y> capability, @Nullable Direction arg) {
        LazyOptional<IEnergyStorage> of = LazyOptional.of(container.getContainer(arg) != null ? () -> this : null);
        return capability.orEmpty(ForgeCapabilities.ENERGY, of.cast()).cast();
    }

    @Override
    public int receiveEnergy(int maxAmount, boolean bl) {
        if (maxAmount <= 0) return 0;
        int inserted = (int) container.insertEnergy(Math.min(maxAmount, container.maxInsert()), bl);
        container.update(entity);
        return inserted;
    }

    @Override
    public int extractEnergy(int maxAmount, boolean bl) {
        if (maxAmount <= 0) return 0;
        int extracted = (int) container.extractEnergy(Math.min(maxAmount, container.maxExtract()), bl);
        container.update(entity);
        return extracted;
    }

    @Override
    public int getEnergyStored() {
        return (int) container.getStoredEnergy();
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) container.getMaxCapacity();
    }

    @Override
    public boolean canExtract() {
        return container.allowsExtraction();
    }

    @Override
    public boolean canReceive() {
        return container.allowsInsertion();
    }

    @Override
    public Serializable getSerializable() {
        return container;
    }

}
