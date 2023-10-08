package dev.tonimatas.mythlib;

import net.fabricmc.api.ModInitializer;

public class MythLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MythLib.init();
    }
}