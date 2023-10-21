package dev.tonimatas.mythlib.registry.forge;

import dev.tonimatas.mythlib.registry.MythRegistry;
import dev.tonimatas.mythlib.registry.forge.ForgeMythRegistry;
import net.minecraft.core.Registry;

public interface MythRegistryImpl {
    static <T> MythRegistry<T> create(Registry<T> registry, String id) {
        return new ForgeMythRegistry<>(registry, id);
    }
}
