package dev.tonimatas.mythlib.registry;

import java.util.Collection;
import java.util.function.Supplier;

public class MythRegistryImpl<T> implements MythRegistry<T> {
    private final MythRegistry<T> parent;
    private final RegistryEntries<T> entries = new RegistryEntries<>();

    public MythRegistryImpl(MythRegistry<T> parent) {
        this.parent = parent;
    }

    @Override
    public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
        return this.entries.add(parent.register(id, supplier));
    }

    @Override
    public Collection<RegistryEntry<T>> getEntries() {
        return entries.getEntries();
    }

    @Override
    public void init() {
    }
}
