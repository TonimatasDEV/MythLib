package dev.tonimatas.mythlib.energy.fabric;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.energy.EnergyApi;
import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.energy.base.fabric.PlatformEnergyManager;
import dev.tonimatas.mythlib.energy.base.fabric.PlatformItemEnergyManager;
import dev.tonimatas.mythlib.item.ItemStackHolder;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

public class EnergyApiImpl {
    public static EnergyContainer getItemEnergyContainer(ItemStackHolder stack) {
        return EnergyApi.isEnergyItem(stack.getStack()) ? new PlatformItemEnergyManager(stack) : null;
    }

    public static EnergyContainer getBlockEnergyContainer(BlockEntity entity, Direction direction) {
        return EnergyApi.isEnergyBlock(entity, direction) ? new PlatformEnergyManager(entity, direction) : null;
    }

    public static boolean isEnergyItem(ItemStack stack) {
        return EnergyStorageUtil.isEnergyStorage(stack);
    }

    public static boolean isEnergyBlock(BlockEntity block, Direction direction) {
        return EnergyStorage.SIDED.find(block.getLevel(), block.getBlockPos(), direction) != null;
    }
}
