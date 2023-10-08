package dev.tonimatas.mythlib.fabric;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.fabric.FabricFluidHolder;
import dev.tonimatas.mythlib.menu.ExtraDataMenuProvider;
import dev.tonimatas.mythlib.menu.ExtraDataMenuProviderWrapper;

@SuppressWarnings("UnstableApiUsage")
public class HooksImpl {
    public static int getBurnTime(ItemStack burnable) {
        Integer burnTime = FuelRegistry.INSTANCE.get(burnable.getItem());
        return burnTime == null ? 0 : burnTime;
    }

    public static TextureAtlasSprite getFluidSprite(FluidHolder fluid) {
        return FluidVariantRendering.getSprite(FabricFluidHolder.of(fluid).toVariant());
    }

    public static int getFluidColor(FluidHolder fluid) {
        return FluidVariantRendering.getColor(FabricFluidHolder.of(fluid).toVariant());
    }

    public static void openMenu(ServerPlayer player, ExtraDataMenuProvider provider) {
        player.openMenu(new ExtraDataMenuProviderWrapper(provider));
    }
}
