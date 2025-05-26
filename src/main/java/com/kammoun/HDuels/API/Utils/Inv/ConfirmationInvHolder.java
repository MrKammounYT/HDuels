package com.kammoun.API.Utils.Inv;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class ConfirmationInvHolder  implements InventoryHolder {


    private Inventory inv;


    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}
