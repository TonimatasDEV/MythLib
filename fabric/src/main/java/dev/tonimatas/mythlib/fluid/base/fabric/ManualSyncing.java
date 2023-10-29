package dev.tonimatas.mythlib.fluid.base.fabric;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

@SuppressWarnings("UnstableApiUsage")
public interface ManualSyncing {
    default void setChanged(TransactionContext transaction) {}
}
