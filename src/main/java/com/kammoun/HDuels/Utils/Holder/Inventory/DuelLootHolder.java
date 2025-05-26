package com.kammoun.HDuels.Utils.Holder.Inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class DuelLootHolder implements InventoryHolder {

    private Inventory inv;
    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}