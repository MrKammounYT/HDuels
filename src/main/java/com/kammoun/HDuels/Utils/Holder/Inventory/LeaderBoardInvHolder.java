package com.kammoun.HDuels.Utils.Holder.Inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class LeaderBoardInvHolder implements InventoryHolder {
    private Inventory inventory;
    private int page;
    private boolean isMax;

    public LeaderBoardInvHolder(int page,boolean isMax) {
        this.page = page;
        this.isMax = isMax;
    }

    public boolean isMaxPage() {
        return isMax;
    }

    public int getCurrentPage() {
        return page;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}
