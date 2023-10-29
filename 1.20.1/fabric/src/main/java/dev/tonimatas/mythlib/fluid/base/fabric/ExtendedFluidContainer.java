package dev.tonimatas.mythlib.fluid.base.fabric;

import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import dev.tonimatas.mythlib.fluid.base.FluidSnapshot;

@SuppressWarnings("UnstableApiUsage")
public abstract class ExtendedFluidContainer extends SnapshotParticipant<FluidSnapshot> {
    @Override
    abstract public void onFinalCommit();

    @Override
    abstract public FluidSnapshot createSnapshot();

    @Override
    abstract public void readSnapshot(FluidSnapshot snapshot);
}
