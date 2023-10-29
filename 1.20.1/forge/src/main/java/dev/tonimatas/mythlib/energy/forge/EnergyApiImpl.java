package dev.tonimatas.mythlib.energy.forge;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import dev.tonimatas.mythlib.energy.EnergyApi;
import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.energy.base.forge.PlatformBlockEnergyManager;
import dev.tonimatas.mythlib.energy.base.forge.PlatformItemEnergyManager;
import dev.tonimatas.mythlib.item.ItemStackHolder;
import org.jetbrains.annotations.Nullable;

public class EnergyApiImpl {
    @Nullable
    public static EnergyContainer getItemEnergyContainer(ItemStackHolder stack) {
        return EnergyApi.isEnergyItem(stack.getStack()) ? new PlatformItemEnergyManager(stack.getStack()) : null;
    }

    @Nullable
    public static EnergyContainer getBlockEnergyContainer(BlockEntity entity, @Nullable Direction direction) {
        return EnergyApi.isEnergyBlock(entity, direction) ? new PlatformBlockEnergyManager(entity, direction) : null;
    }

    public static boolean isEnergyItem(ItemStack stack) {
        return stack.getCapability(ForgeCapabilities.ENERGY).isPresent();
    }

    public static boolean isEnergyBlock(BlockEntity block, @Nullable Direction direction) {
        return block.getCapability(ForgeCapabilities.ENERGY, direction).isPresent();
    }
}
