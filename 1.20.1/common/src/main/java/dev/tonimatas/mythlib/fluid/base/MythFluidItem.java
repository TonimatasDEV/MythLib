package dev.tonimatas.mythlib.fluid.base;

import net.minecraft.world.item.ItemStack;
import dev.tonimatas.mythlib.util.Updatable;

public interface MythFluidItem<T extends ItemFluidContainer & Updatable<ItemStack>> {
    T getFluidContainer(ItemStack holder);
}