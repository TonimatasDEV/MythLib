package dev.tonimatas.mythlib.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface MythRegistry<T> {
    <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier);

    Collection<RegistryEntry<T>> getEntries();

    default Stream<RegistryEntry<T>> stream() {
        return getEntries().stream();
    }

    default Stream<T> boundStream() {
        return stream().map(RegistryEntry::get);
    }

    void init();

    @ExpectPlatform
    static <T> MythRegistry<T> create(Registry<T> registry, String id) {
        throw new NotImplementedException();
    }
}
