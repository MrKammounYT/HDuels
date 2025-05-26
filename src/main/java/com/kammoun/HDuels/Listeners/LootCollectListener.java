package com.kammoun.HDuels.Listeners;

import com.kammoun.HDuels.API.Inventory.KMenus;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelLootHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LootCollectListener extends KMenus {
    public LootCollectListener(Core coreManager) {
        super(coreManager);
    }

    @EventHandler
    public void onLootCollect(InventoryClickEvent e) {
        if (!isValidInventoryHolder(e.getInventory())) return;
        if (!(e.getInventory().getHolder() instanceof DuelLootHolder)) return;

        e.setCancelled(true); // Cancel all interactions

        Player p = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedItem == null || clickedItem.getType().isAir() || !isInDuel(p) || !isLootCollectPhase(p)) {
            return;
        }

        HDuel duel = core.getPlayerManager().getPlayer(p).getPlayerCurrentDuel();
        List<ItemStack> lootInventory = new ArrayList<>(duel.getItemsInventory()); // Copy the list

        // Iterate over the loot inventory
        for (ItemStack item : lootInventory) {
            if (item == null) continue; // **Fix: Skip null items to prevent errors**

            if (item.isSimilar(clickedItem)) {
                int remainingAmount = clickedItem.getAmount();

                if (item.getAmount() > remainingAmount) {
                    item.setAmount(item.getAmount() - remainingAmount);
                } else {
                    lootInventory.remove(item); // Remove the item completely if all taken
                }

                // Try to add to player's inventory
                HashMap<Integer, ItemStack> excess = p.getInventory().addItem(clickedItem.clone());

                // Drop excess items if inventory is full
                for (ItemStack extra : excess.values()) {
                    p.getWorld().dropItemNaturally(p.getLocation(), extra);
                }

                // Update the loot inventory in the duel object
                duel.setItemsInventory(lootInventory);

                // Refresh the loot menu
                duel.openLootCollectInventory(p);
                return;
            }
        }
    }

    private boolean isLootCollectPhase(Player p) {
        HDuel duel = core.getPlayerManager().getPlayer(p).getPlayerCurrentDuel();
        return duel != null
                && duel.getDuelArena() != null
                && duel.getDuelArena().getArenaState() == GameState.ITEMCOLLECT;
    }

    private boolean isInDuel(Player p) {
        if (core == null) return false;
        if (core.getPlayerManager() == null) return false;
        return core.getPlayerManager().getPlayer(p) != null && core.getPlayerManager().getPlayer(p).isInDuel();
    }

}

