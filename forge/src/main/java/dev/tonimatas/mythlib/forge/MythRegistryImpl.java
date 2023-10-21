package dev.tonimatas.mythlib.forge;

import dev.tonimatas.mythlib.registry.MythRegistry;
import net.minecraft.core.Registry;

public interface MythRegistryImpl {
    static <T> MythRegistry<T> create(Registry<T> registry, String id) {
        return new ForgeMythRegistry<>(registry, id);
    }
}