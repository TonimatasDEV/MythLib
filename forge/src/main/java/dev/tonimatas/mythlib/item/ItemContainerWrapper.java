package dev.tonimatas.mythlib.item;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import dev.tonimatas.mythlib.AutoSerializable;
import dev.tonimatas.mythlib.util.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemContainerWrapper extends InvWrapper implements ICapabilityProvider, AutoSerializable {

    private final SerializableContainer serializableContainer;

    public ItemContainerWrapper(SerializableContainer inv) {
        super(inv);
        this.serializableContainer = inv;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
        return capability == ForgeCapabilities.ITEM_HANDLER ? LazyOptional.of(() -> this).cast() : LazyOptional.empty();
    }

    @Override
    public Serializable getSerializable() {
        return serializableContainer;
    }
}
