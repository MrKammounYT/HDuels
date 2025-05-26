package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SurvivalGuide {
    public static boolean isSurvivalGuide(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }
        NBTItem nbtItem = new NBTItem(itemStack);
        return "SurvivalGuide".equals(nbtItem.getString("customKey"));
    }

}
