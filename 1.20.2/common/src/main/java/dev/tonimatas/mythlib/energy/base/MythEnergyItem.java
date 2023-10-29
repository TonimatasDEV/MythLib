package dev.tonimatas.mythlib.energy.base;

import net.minecraft.world.item.ItemStack;
import dev.tonimatas.mythlib.util.Updatable;

public interface MythEnergyItem<T extends EnergyContainer & Updatable<ItemStack>> {
    T getEnergyStorage(ItemStack holder);
}
