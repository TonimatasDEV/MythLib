package dev.tonimatas.mythlib.util.forge;

import dev.tonimatas.mythlib.menu.ExtraDataMenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class HooksImpl {
    public static int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, null);
    }

    public static void openMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        player.openMenu(provider, (data) -> provider.writeExtraData(player, data));
    }
}
