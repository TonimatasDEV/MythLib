package dev.tonimatas.mythlib.util.fabric;

import dev.tonimatas.mythlib.menu.ExtraDataMenuProvider;
import dev.tonimatas.mythlib.menu.ExtraDataMenuProviderWrapper;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class HooksImpl {
    public static int getBurnTime(ItemStack burnable) {
        Integer burnTime = FuelRegistry.INSTANCE.get(burnable.getItem());
        return burnTime == null ? 0 : burnTime;
    }

    public static void openMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        player.openMenu(new ExtraDataMenuProviderWrapper(provider));
    }
}
