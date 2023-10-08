package dev.tonimatas.mythlib.fabric;

import dev.tonimatas.mythlib.registry.MythRegistry;
import net.minecraft.core.Registry;

public class MythRegistriesImpl {
    public static <T> MythRegistry<T> create(Registry<T> registry, String id) {
        return new FabricMythRegistry<>(registry, id);
    }
}
