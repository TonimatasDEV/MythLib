package dev.tonimatas.mythlib.energy.impl;

import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.energy.base.EnergySnapshot;

public class SimpleEnergySnapshot implements EnergySnapshot {
    private final long energy;

    public SimpleEnergySnapshot(EnergyContainer container) {
        this.energy = container.getStoredEnergy();
    }

    @Override
    public void loadSnapshot(EnergyContainer container) {
        container.setEnergy(energy);
    }
}
