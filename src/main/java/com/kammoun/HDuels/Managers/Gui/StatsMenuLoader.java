package com.kammoun.HDuels.Managers.Gui;

import com.kammoun.HDuels.API.Loaders.KItemLoader;
import com.kammoun.HDuels.API.Loaders.KMenuLoader;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.CopyOnWriteArrayList;

public class StatsMenuLoader extends KMenuLoader {
    
    private final CopyOnWriteArrayList<KammounItem> Items = new CopyOnWriteArrayList<KammounItem>();
    
    public StatsMenuLoader(Main main, String ConfigName) {
        super(main, ConfigName);
        this.InventoryName = config.getString("Inventory.name");
        this.InventorySize = config.getInt("Inventory.size");
        loadItems();
    }

    @Override
    public void loadItems() {
        for (String item : config.getConfigurationSection("Inventory.Items").getKeys(false)){
            Items.add(KItemLoader.getOptionItemFromConfig(config.getConfigurationSection("Inventory.Items." + item)));
        }
        Main.Inform("&aLoaded &e"+Items.size() + " &aItems to the stats Menu!");
    }

    @Override
    public void openInventory(Player p) {
        super.openInventory(p);
        Inventory inv = Bukkit.createInventory(new DuelInventoryHolder(),InventorySize, ChatFormater.Color(InventoryName));
        for(KammounItem i : Items){
            inv.setItem(i.getSlot(),replacePlaceHoldersFromItemStack(p,i.getItem()));
        }
        p.openInventory(inv);
    }

}
