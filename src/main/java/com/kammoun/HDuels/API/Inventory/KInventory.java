package com.kammoun.HDuels.API.Inventory;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class KInventory {

    private final String InventoryName;
    private final int size;
    private final InventoryHolder inventoryHolder;
    private KammounItem goBackItem;
    private KammounItem NextPage;
    private KammounItem PreviousPage;

    public KInventory(String inventoryName, int size, InventoryHolder inventoryHolder) {
        this.InventoryName = inventoryName;
        this.size = size;
        this.inventoryHolder = inventoryHolder;
    }

    public void setGoBackItem(KammounItem goBackItem) {
        this.goBackItem = goBackItem;
    }

    public void setNextPage(KammounItem nextPage) {
        NextPage = nextPage;
    }

    public void setPreviousPage(KammounItem previousPage) {
        PreviousPage = previousPage;
    }

    public Inventory getInventory() {
        if(size %9 !=0)return  Bukkit.createInventory(inventoryHolder,54, ChatFormater.Color(InventoryName));
        return  Bukkit.createInventory(inventoryHolder,size, ChatFormater.Color(InventoryName));
    }
}
