package dev.tonimatas.mythlib.client.forge;

import dev.tonimatas.mythlib.client.RegistryClientHelpers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class RegistryClientHelpersImpl {
    public static <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerMenu(MenuType<? extends M> menuType, RegistryClientHelpers.ScreenConstructor<M, U> screenConstructor) {
        MenuScreens.register(menuType, screenConstructor::create);
    }
}
