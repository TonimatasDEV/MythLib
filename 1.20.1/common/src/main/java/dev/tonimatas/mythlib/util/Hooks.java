package dev.tonimatas.mythlib.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.tonimatas.mythlib.menu.ExtraDataMenuProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.NotImplementedException;

public class Hooks {
    @ExpectPlatform
    public static int getBurnTime(ItemStack stack) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void openMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        throw new NotImplementedException();
    }
}
