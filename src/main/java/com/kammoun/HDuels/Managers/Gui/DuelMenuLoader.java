package com.kammoun.HDuels.Managers.Gui;

import com.kammoun.HDuels.API.Loaders.KItemLoader;
import com.kammoun.HDuels.API.Loaders.KMenuLoader;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelInventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static com.kammoun.HDuels.Utils.Others.PlaceHolderManager.ReplaceOperationPlaceHolder;

public class DuelMenuLoader extends KMenuLoader {

    private KammounItem JoinQueue;
    private KammounItem LeaveQueue;
    private KammounItem Rewards;
    private KammounItem Stats;
    private final Core core;
    public DuelMenuLoader(Core core,Main main, String ConfigName) {
        super(main, ConfigName);
        this.core = core;
        loadItems();
        loadInventoryOptions();
    }

    @Override
    public void loadInventoryOptions() {
        super.loadInventoryOptions();
        this.InventoryName = config.getString("Inventory.name");
        this.InventorySize = config.getInt("Inventory.size");
    }

    @Override
    public void loadItems() {
        super.loadItems();
        this.LeaveQueue = KItemLoader.getOptionItemFromConfig(config.getConfigurationSection("Inventory.Items.LeaveQueue"));
        this.JoinQueue = KItemLoader.getOptionItemFromConfig(config.getConfigurationSection("Inventory.Items.JoinQueue"));
        this.Rewards = KItemLoader.getOptionItemFromConfig(config.getConfigurationSection("Inventory.Items.Rewards"));
        this.Stats = KItemLoader.getOptionItemFromConfig(config.getConfigurationSection("Inventory.Items.Stats"));
    }

    @Override
    public void openInventory(Player p) {
        super.openInventory(p);
        Inventory inv = Bukkit.createInventory(new DuelInventoryHolder(),InventorySize, ChatFormater.Color(InventoryName));
        inv.setItem(Rewards.getSlot(), replacePlaceHoldersFromItemStack(p, Rewards.getItem()));
        inv.setItem(Stats.getSlot(), replacePlaceHoldersFromItemStack(p, Stats.getItem()));
        if(!core.getQueueManager().isInQueue(p)){
            inv.setItem(JoinQueue.getSlot(), replacePlaceHoldersFromItemStack(p,JoinQueue.getItem()));
        }else{
            inv.setItem(LeaveQueue.getSlot(), replacePlaceHoldersFromItemStack(p,LeaveQueue.getItem()));
        }
        p.openInventory(inv);
    }


    public KammounItem getJoinQueue() {
        return JoinQueue;
    }


    public KammounItem getLeaveQueue() {
        return LeaveQueue;
    }

    public KammounItem getRewards() {
        return Rewards;
    }

    public KammounItem getStats() {
        return Stats;
    }
}
