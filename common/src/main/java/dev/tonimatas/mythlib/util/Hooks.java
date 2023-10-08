package dev.tonimatas.mythlib.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.menu.ExtraDataMenuProvider;
import org.apache.commons.lang3.NotImplementedException;

public class Hooks {
    @ExpectPlatform
    public static int getBurnTime(ItemStack stack) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static TextureAtlasSprite getFluidSprite(FluidHolder fluid) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static int getFluidColor(FluidHolder fluid) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void openMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        throw new NotImplementedException();
    }
}
