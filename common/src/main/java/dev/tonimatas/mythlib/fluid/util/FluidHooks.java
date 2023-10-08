package dev.tonimatas.mythlib.fluid.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.PlatformFluidHandler;
import dev.tonimatas.mythlib.fluid.base.PlatformFluidItemHandler;
import dev.tonimatas.mythlib.item.ItemStackHolder;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FluidHooks {
    @ExpectPlatform
    public static FluidHolder newFluidHolder(Fluid fluid, long amount, @Nullable CompoundTag tag) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static FluidHolder fluidFromCompound(CompoundTag compoundTag) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static FluidHolder emptyFluid() {
        throw new NotImplementedException();
    }

    public static long buckets(double buckets) {
        return (long) (buckets * getBucketAmount());
    }

    @ExpectPlatform
    public static PlatformFluidItemHandler getItemFluidManager(ItemStack stack) {
        throw new NotImplementedException("Item Fluid Manager not Implemented");
    }

    @ExpectPlatform
    public static PlatformFluidHandler getBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        throw new NotImplementedException("Block Entity Fluid manager not implemented");
    }

    @ExpectPlatform
    public static boolean isFluidContainingBlock(BlockEntity entity, @Nullable Direction direction) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static boolean isFluidContainingItem(ItemStack stack) {
        throw new NotImplementedException();
    }

    public static Optional<PlatformFluidHandler> safeGetBlockFluidManager(BlockEntity entity, @Nullable Direction direction) {
        if (entity == null) return Optional.empty();
        return isFluidContainingBlock(entity, direction) ? Optional.of(getBlockFluidManager(entity, direction)) : Optional.empty();
    }

    public static Optional<PlatformFluidItemHandler> safeGetItemFluidManager(ItemStack stack) {
        return isFluidContainingItem(stack) ? Optional.of(getItemFluidManager(stack)) : Optional.empty();
    }

    public static long moveFluid(PlatformFluidHandler from, PlatformFluidHandler to, FluidHolder fluid) {
        FluidHolder extracted = from.extractFluid(fluid, true);
        long inserted = to.insertFluid(extracted, true);
        from.extractFluid(newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(extracted, false);
    }

    public static long moveItemToStandardFluid(PlatformFluidItemHandler from, ItemStack sender, PlatformFluidHandler to, FluidHolder fluid) {
        ItemStackHolder senderHolder = new ItemStackHolder(sender);

        FluidHolder extracted = from.extractFluid(senderHolder.copy(), fluid, true);
        long inserted = to.insertFluid(extracted, true);
        from.extractFluid(senderHolder, newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(extracted, false);
    }

    public static long moveStandardToItemFluid(PlatformFluidHandler from, PlatformFluidItemHandler to, ItemStack receiver, FluidHolder fluid) {
        ItemStackHolder receiverHolder = new ItemStackHolder(receiver);

        FluidHolder extracted = from.extractFluid(fluid, true);
        long inserted = to.insertFluid(receiverHolder.copy(), extracted, true);
        from.extractFluid(newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(receiverHolder, extracted, false);
    }

    public static long moveItemToItemFluid(PlatformFluidItemHandler from, ItemStack sender, PlatformFluidItemHandler to, ItemStack receiver, FluidHolder fluid) {
        ItemStackHolder senderHolder = new ItemStackHolder(sender);
        ItemStackHolder receiverHolder = new ItemStackHolder(receiver);
        FluidHolder extracted = from.extractFluid(senderHolder.copy(), fluid, true);
        long inserted = to.insertFluid(receiverHolder.copy(), extracted, true);
        from.extractFluid(senderHolder, newFluidHolder(fluid.getFluid(), inserted, fluid.getCompound()), false);
        return to.insertFluid(receiverHolder, extracted, false);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static long safeMoveFluid(Optional<PlatformFluidHandler> from, Optional<PlatformFluidHandler> to, FluidHolder fluid) {
        return from.flatMap(f -> to.map(t -> moveFluid(f, t, fluid))).orElse(0L);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static long safeMoveItemToStandard(Optional<PlatformFluidItemHandler> from, ItemStack sender, Optional<PlatformFluidHandler> to, FluidHolder fluid) {
        return from.flatMap(f -> to.map(t -> moveItemToStandardFluid(f, sender, t, fluid))).orElse(0L);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static long safeMoveStandardToItem(Optional<PlatformFluidHandler> from, Optional<PlatformFluidItemHandler> to, ItemStack receiver, FluidHolder fluid) {
        return from.flatMap(f -> to.map(t -> moveStandardToItemFluid(f, t, receiver, fluid))).orElse(0L);
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static long safeMoveItemToItem(Optional<PlatformFluidItemHandler> from, ItemStack sender, Optional<PlatformFluidItemHandler> to, ItemStack receiver, FluidHolder fluid) {
        return from.flatMap(f -> to.map(t -> moveItemToItemFluid(f, sender, t, receiver, fluid))).orElse(0L);
    }

    public static long moveBlockToBlockFluid(BlockEntity from, @Nullable Direction fromDirection, BlockEntity to, @Nullable Direction toDirection, FluidHolder fluid) {
        return safeMoveFluid(safeGetBlockFluidManager(from, fromDirection), safeGetBlockFluidManager(to, toDirection), fluid);
    }

    public static long moveBlockToItemFluid(BlockEntity from, @Nullable Direction fromDirection, ItemStack to, FluidHolder fluid) {
        return safeMoveStandardToItem(safeGetBlockFluidManager(from, fromDirection), safeGetItemFluidManager(to), to, fluid);
    }

    public static long moveItemToBlockFluid(ItemStack from, BlockEntity to, @Nullable Direction toDirection, FluidHolder fluid) {
        return safeMoveItemToStandard(safeGetItemFluidManager(from), from, safeGetBlockFluidManager(to, toDirection), fluid);
    }

    public static long moveItemToItemFluid(ItemStack from, ItemStack to, FluidHolder fluid) {
        return safeMoveItemToItem(safeGetItemFluidManager(from), from, safeGetItemFluidManager(to), to, fluid);
    }

    @ExpectPlatform
    public static long toMillibuckets(long amount) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getBucketAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getBottleAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getBlockAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getIngotAmount() {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static long getNuggetAmount() {
        throw new NotImplementedException();
    }

    public static void writeToBuffer(FluidHolder holder, FriendlyByteBuf buffer) {
        if (holder.isEmpty()) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeVarInt(BuiltInRegistries.FLUID.getId(holder.getFluid()));
            buffer.writeVarLong(holder.getFluidAmount());
            buffer.writeNbt(holder.getCompound());
        }
    }

    public static FluidHolder readFromBuffer(FriendlyByteBuf buffer) {
        if (!buffer.readBoolean()) return FluidHooks.emptyFluid();
        Fluid fluid = BuiltInRegistries.FLUID.byId(buffer.readVarInt());
        long amount = buffer.readVarLong();
        return FluidHolder.of(fluid, amount, buffer.readNbt());
    }
}
