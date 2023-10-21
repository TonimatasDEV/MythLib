package dev.tonimatas.mythlib.registry.fabric;

import dev.tonimatas.mythlib.registry.MythRegistry;
import net.minecraft.core.Registry;

public interface MythRegistryImpl {
    static <T> MythRegistry<T> create(Registry<T> registry, String id) {
        return new FabricMythRegistry<>(registry, id);
    }
}
