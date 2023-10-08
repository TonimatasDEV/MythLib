package dev.tonimatas.mythlib;

import dev.tonimatas.mythlib.energy.base.MythEnergyBlock;
import dev.tonimatas.mythlib.energy.base.MythEnergyItem;
import dev.tonimatas.mythlib.energy.base.forge.ForgeEnergyContainer;
import dev.tonimatas.mythlib.energy.base.forge.ForgeItemEnergyContainer;
import dev.tonimatas.mythlib.fluid.base.MythFluidBlock;
import dev.tonimatas.mythlib.fluid.base.MythFluidItem;
import dev.tonimatas.mythlib.fluid.base.forge.ForgeFluidContainer;
import dev.tonimatas.mythlib.fluid.base.forge.ForgeItemFluidContainer;
import dev.tonimatas.mythlib.item.ItemContainerBlock;
import dev.tonimatas.mythlib.item.ItemContainerWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(MythLib.MOD_ID)
public class MythLibForge {
    public MythLibForge() {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        MythLib.init();

        eventBus.addGenericListener(BlockEntity.class, MythLibForge::attachBlockCapabilities);
        eventBus.addGenericListener(ItemStack.class, MythLibForge::attachItemCapabilities);

        eventBus.register(this);
    }

    public static void attachBlockCapabilities(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof MythEnergyBlock<?> energyBlock) {
            event.addCapability(new ResourceLocation(MythLib.MOD_ID, "energy"), new ForgeEnergyContainer<>(energyBlock.getEnergyStorage(), event.getObject()));
        }

        if (event.getObject() instanceof MythFluidBlock<?> fluidHoldingBlock) {
            event.addCapability(new ResourceLocation(MythLib.MOD_ID, "fluid"), new ForgeFluidContainer<>(fluidHoldingBlock.getFluidContainer(), event.getObject()));
        }

        if (event.getObject() instanceof ItemContainerBlock itemContainerBlock) {
            event.addCapability(new ResourceLocation(MythLib.MOD_ID, "item"), new ItemContainerWrapper(itemContainerBlock.getContainer()));
        }
    }

    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof MythEnergyItem<?> energyItem) {
            event.addCapability(new ResourceLocation(MythLib.MOD_ID, "energy"), new ForgeItemEnergyContainer<>(energyItem.getEnergyStorage(event.getObject()), event.getObject()));
        }

        if (event.getObject().getItem() instanceof MythFluidItem<?> fluidHoldingItem) {
            event.addCapability(new ResourceLocation(MythLib.MOD_ID, "fluid"), new ForgeItemFluidContainer<>(fluidHoldingItem.getFluidContainer(event.getObject()), event.getObject()));
        }
    }
}
