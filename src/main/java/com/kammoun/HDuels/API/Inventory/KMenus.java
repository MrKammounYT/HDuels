package com.kammoun.HDuels.API.Inventory;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Managers.Core;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class KMenus implements Listener {


    public final Core core;

    public KMenus(Core coreManager) {
        this.core = coreManager;
    }

    public boolean isValidItem(ItemStack itemStack){
        return itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName();
    }

    public boolean isValidInventoryHolder(Inventory e){
        return e!= null &&e.getHolder() != null;

    }

    public boolean hasDisplayName(ItemStack e, String name) {
        if (e == null || e.getType() == Material.AIR || !e.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = e.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return false;
        }
        String displayName = meta.getDisplayName().trim();
        String formattedName = ChatFormater.Color(ChatFormater.getHexColorCodes().uncodeHex(name)).trim();

        return ChatColor.stripColor(displayName).equals(ChatColor.stripColor(formattedName));
    }
    public boolean hasDisplayName(ItemStack e,ItemStack itemStack) {
        if (e == null || !e.hasItemMeta() || e.getItemMeta().getDisplayName() == null || itemStack == null ||itemStack.getItemMeta() == null) {
            return false;
        }
        ItemMeta meta = e.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return false;
        }
         meta = itemStack.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return false;
        }
        String displayName = e.getItemMeta().getDisplayName().trim();
        String formattedName = ChatFormater.Color(ChatFormater.getHexColorCodes().uncodeHex(itemStack.getItemMeta().getDisplayName())).trim();
        return ChatColor.stripColor(displayName).equals(ChatColor.stripColor(formattedName));
    }

    public boolean containsDisplayName(ItemStack e, String name){
        return Objects.requireNonNull(e.getItemMeta()).getDisplayName().contains(ChatFormater.Color(name));
    }
}
