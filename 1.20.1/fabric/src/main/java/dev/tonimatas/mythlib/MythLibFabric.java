package dev.tonimatas.mythlib;

import dev.tonimatas.mythlib.energy.base.EnergyContainer;
import dev.tonimatas.mythlib.energy.base.MythEnergyBlock;
import dev.tonimatas.mythlib.energy.base.MythEnergyItem;
import dev.tonimatas.mythlib.energy.base.fabric.FabricBlockEnergyContainer;
import dev.tonimatas.mythlib.energy.base.fabric.FabricItemEnergyContainer;
import dev.tonimatas.mythlib.fluid.base.FluidContainer;
import dev.tonimatas.mythlib.fluid.base.MythFluidBlock;
import dev.tonimatas.mythlib.fluid.base.MythFluidItem;
import dev.tonimatas.mythlib.fluid.base.fabric.FabricBlockFluidContainer;
import dev.tonimatas.mythlib.fluid.base.fabric.FabricItemFluidContainer;
import dev.tonimatas.mythlib.item.ItemContainerBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.impl.transfer.item.InventoryStorageImpl;
import team.reborn.energy.api.EnergyStorage;

public class MythLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MythLib.init();

        EnergyStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof MythEnergyBlock<?> attachment) {
                EnergyContainer container = attachment.getEnergyStorage().getContainer(context);
                return container == null ? null : new FabricBlockEnergyContainer(container, attachment.getEnergyStorage(), blockEntity);
            }

            return null;
        });

        EnergyStorage.ITEM.registerFallback((itemStack, context) -> {
            if (itemStack.getItem() instanceof MythEnergyItem<?> attachment) {
                EnergyContainer energyStorage = attachment.getEnergyStorage(itemStack);
                return energyStorage == null ? null : new FabricItemEnergyContainer(context, energyStorage);
            }

            return null;
        });

        FluidStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof MythFluidBlock<?> attachment) {
                FluidContainer container = attachment.getFluidContainer().getContainer(context);
                return container == null ? null : new FabricBlockFluidContainer(container, attachment.getFluidContainer(), blockEntity);
            }

            return null;
        });


        FluidStorage.ITEM.registerFallback((itemStack, context) -> {
            if (itemStack.getItem() instanceof MythFluidItem<?> attachment) {
                FluidContainer fluidContainer = attachment.getFluidContainer(itemStack);
                return fluidContainer == null ? null : new FabricItemFluidContainer(context, fluidContainer);
            }

            return null;
        });

        ItemStorage.SIDED.registerFallback((world, pos, state, blockEntity, context) -> {
            if (blockEntity instanceof ItemContainerBlock energyContainer) {
                return InventoryStorageImpl.of(energyContainer.getContainer(), context);
            }
            return null;
        });
    }
}