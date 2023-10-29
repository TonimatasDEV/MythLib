package dev.tonimatas.mythlib.fluid.base.fabric;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import dev.tonimatas.mythlib.fluid.base.FluidContainer;
import dev.tonimatas.mythlib.fluid.base.FluidHolder;
import dev.tonimatas.mythlib.fluid.base.FluidSnapshot;
import dev.tonimatas.mythlib.util.Updatable;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

@SuppressWarnings("UnstableApiUsage")
public class FabricBlockFluidContainer extends ExtendedFluidContainer implements Storage<FluidVariant>, ManualSyncing {
    private final FluidContainer container;
    private final Updatable<BlockEntity> blockEntity;
    private final BlockEntity block;

    public FabricBlockFluidContainer(FluidContainer container, Updatable<BlockEntity> updatable, BlockEntity entity) {
        this.container = container;
        this.blockEntity = updatable;
        this.block = entity;
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return container.insertFluid(FabricFluidHolder.of(resource, maxAmount), false);
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        updateSnapshots(transaction);
        return container.extractFluid(FabricFluidHolder.of(resource, maxAmount), false).getFluidAmount();
    }

    @Override
    public @NotNull Iterator<StorageView<FluidVariant>> iterator() {
        List<FluidHolder> fluids = container.getFluids();
        return IntStream.range(0, fluids.size()).mapToObj(index -> new WrappedFluidHolder(this, fluids.get(index), container::extractFromSlot, container.getTankCapacity(index))).map(holder -> (StorageView<FluidVariant>) holder).iterator();
    }

    @Override
    public FluidSnapshot createSnapshot() {
        return container.createSnapshot();
    }

    @Override
    public void readSnapshot(FluidSnapshot snapshot) {
        container.readSnapshot(snapshot);
    }

    @Override
    public void onFinalCommit() {
        blockEntity.update(block);
    }

}
