package com.kammoun.HDuels.API.Loaders;

import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import com.kammoun.HDuels.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.kammoun.HDuels.Utils.Others.PlaceHolderManager.ReplaceOperationPlaceHolder;

public abstract class KMenuLoader {


    protected final YamlConfiguration config;
    public String InventoryName;
    public int InventorySize;
    public KammounItem close;
    public KammounItem back;
    public KammounItem nextPage;
    public KammounItem previousPage;
    private final Main main;
    public KMenuLoader(Main main,String ConfigName) {
        this.main = main;
        this.config = YamlConfiguration.loadConfiguration(createConfigurationFile(ConfigName));
    }

    public  File createConfigurationFile(String fileName){
        File file =new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            main.saveResource(fileName,false);
        }
        return file;
    }

    public  void openInventory(Player p, int page){

    }

    public  void openInventory(Player p){

    }

    public  void loadInventoryOptions(){

    }
    public  void loadItems(){
        // TODO: Implement loading items
    }
    public ItemStack replacePlaceHoldersFromItemStack(Player p, ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();

        // Replace placeholders in display name
        if (meta.hasDisplayName()) {
            String displayName = meta.getDisplayName();
            displayName = ReplaceOperationPlaceHolder(displayName, p);
            meta.setDisplayName(displayName);
        }

        // Replace placeholders in lore
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            List<String> updatedLore = new ArrayList<>();

            for (String line : lore) {
                updatedLore.add(ReplaceOperationPlaceHolder(line, p));
            }

            meta.setLore(updatedLore);
        }

        item.setItemMeta(meta);
        return item;
    }


}
