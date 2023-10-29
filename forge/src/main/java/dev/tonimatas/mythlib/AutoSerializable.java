package dev.tonimatas.mythlib;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import dev.tonimatas.mythlib.util.Serializable;

public interface AutoSerializable extends INBTSerializable<CompoundTag> {
    Serializable getSerializable();

    @Override
    default CompoundTag serializeNBT() {
        return getSerializable().serialize(new CompoundTag());
    }

    @Override
    default void deserializeNBT(CompoundTag arg) {
        getSerializable().deserialize(arg);
    }
}
