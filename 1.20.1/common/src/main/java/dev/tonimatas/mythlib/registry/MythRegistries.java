package dev.tonimatas.mythlib.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import org.apache.commons.lang3.NotImplementedException;

public class MythRegistries {
    @ExpectPlatform
    public static <T> MythRegistry<T> create(Registry<T> registry, String id) {
        throw new NotImplementedException();
    }
}
