package dev.tonimatas.mythlib.energy;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.item.ItemStackHolder;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnergyApi {
    @ExpectPlatform
    @Nullable
    public static EnergyContainer getItemEnergyContainer(ItemStackHolder stack) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    @Nullable
    public static EnergyContainer getBlockEnergyContainer(BlockEntity entity, @Nullable Direction direction) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    @Contract(pure = true)
    public static boolean isEnergyItem(ItemStack stack) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    @Contract(pure = true)
    public static boolean isEnergyBlock(BlockEntity stack, @Nullable Direction direction) {
        throw new NotImplementedException();
    }

    public static long distributeEnergyNearby(BlockEntity energyBlock, long amount) {
        BlockPos blockPos = energyBlock.getBlockPos();
        Level level = energyBlock.getLevel();
        if (level == null) return 0;
        EnergyContainer internalEnergy = getBlockEnergyContainer(energyBlock, null);
        long amountToDistribute = internalEnergy.extractEnergy(amount, true);
        if (amountToDistribute == 0) return 0;
        List<EnergyContainer> list = Direction.stream()
                .map(direction -> level.getBlockEntity(blockPos.relative(direction)))
                .filter(blockEntity -> blockEntity != null && isEnergyBlock(blockEntity, null))
                .map(blockEntity -> getBlockEnergyContainer(blockEntity, null)).toList();
        int receiverCount = list.size();
        for (EnergyContainer energy : list) {
            System.out.println(energy.getStoredEnergy());
            if (energy == null) continue;
            long inserted = moveEnergy(internalEnergy, energy, amountToDistribute / receiverCount, false);
            amountToDistribute -= inserted;
            receiverCount--;
        }
        return amount - amountToDistribute;
    }

    public static long moveEnergy(EnergyContainer from, EnergyContainer to, long amount, boolean simulate) {
        long extracted = from.extractEnergy(amount, true);
        long inserted = to.insertEnergy(extracted, true);
        long simulatedExtraction = from.extractEnergy(inserted, true);
        if (!simulate && inserted > 0 && simulatedExtraction == inserted) {
            from.extractEnergy(inserted, false);
            to.insertEnergy(inserted, false);
        }
        return Math.max(0, inserted);
    }

    public static long moveEnergy(ItemStackHolder from, ItemStackHolder to, long amount, boolean simulate) {
        if (!isEnergyItem(from.getStack()) || !isEnergyItem(to.getStack())) return 0;
        EnergyContainer fromEnergy = getItemEnergyContainer(from);
        EnergyContainer toEnergy = getItemEnergyContainer(to);
        return moveEnergy(fromEnergy, toEnergy, amount, simulate);
    }

    public static long moveEnergy(BlockEntity from, BlockEntity to, long amount, boolean simulate) {
        if (!isEnergyBlock(from, null) || !isEnergyBlock(to, null)) return 0;
        EnergyContainer fromEnergy = getBlockEnergyContainer(from, null);
        EnergyContainer toEnergy = getBlockEnergyContainer(to, null);
        return moveEnergy(fromEnergy, toEnergy, amount, simulate);
    }

    public static long moveEnergy(BlockEntity from, Direction direction, ItemStackHolder to, long amount, boolean simulate) {
        if (!isEnergyBlock(from, direction) || !isEnergyItem(to.getStack())) return 0;
        EnergyContainer fromEnergy = getBlockEnergyContainer(from, direction);
        EnergyContainer toEnergy = getItemEnergyContainer(to);
        return moveEnergy(fromEnergy, toEnergy, amount, simulate);
    }

    public static long moveEnergy(ItemStackHolder from, BlockEntity to, Direction direction, long amount, boolean simulate) {
        if (!isEnergyItem(from.getStack()) || !isEnergyBlock(to, direction)) return 0;
        EnergyContainer fromEnergy = getItemEnergyContainer(from);
        EnergyContainer toEnergy = getBlockEnergyContainer(to, direction);
        return moveEnergy(fromEnergy, toEnergy, amount, simulate);
    }
}
