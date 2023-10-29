package dev.tonimatas.mythlib.energy.base.forge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.energy.base.EnergySnapshot;
import dev.tonimatas.mythlib.energy.impl.SimpleEnergySnapshot;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record PlatformItemEnergyManager(IEnergyStorage energy) implements EnergyContainer {

    public PlatformItemEnergyManager(ItemStack stack) {
        this(stack.getCapability(ForgeCapabilities.ENERGY).orElseThrow(IllegalArgumentException::new));
    }

    @Override
    public long insertEnergy(long maxAmount, boolean simulate) {
        return energy.receiveEnergy((int) maxAmount, simulate);
    }

    @Override
    public long extractEnergy(long maxAmount, boolean simulate) {
        return energy.extractEnergy((int) maxAmount, simulate);
    }

    @Override
    public void setEnergy(long energy) {
        if (energy > this.energy.getEnergyStored()) {
            this.energy.receiveEnergy((int) (energy - this.energy.getEnergyStored()), false);
        } else if (energy < this.energy.getEnergyStored()) {
            this.energy.extractEnergy((int) (this.energy.getEnergyStored() - energy), false);
        }
    }

    @Override
    public long getStoredEnergy() {
        return energy.getEnergyStored();
    }

    @Override
    public long getMaxCapacity() {
        return energy.getMaxEnergyStored();
    }

    @Override
    public long maxInsert() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long maxExtract() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean allowsInsertion() {
        return energy.canReceive();
    }

    @Override
    public boolean allowsExtraction() {
        return energy.canExtract();
    }

    @Override
    public EnergySnapshot createSnapshot() {
        return new SimpleEnergySnapshot(this);
    }

    @Override
    public void deserialize(CompoundTag nbt) {

    }

    @Override
    public CompoundTag serialize(CompoundTag nbt) {
        return nbt;
    }

    @Override
    public void clearContent() {
        setEnergy(0);
    }
}
