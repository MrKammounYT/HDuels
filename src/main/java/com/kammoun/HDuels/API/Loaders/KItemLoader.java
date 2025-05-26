package com.kammoun.HDuels.API.Loaders;

import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.ArrayList;
import java.util.List;

public class KItemLoader {
    public static KammounItem getOptionItemFromConfig(ConfigurationSection configurationSection) {
        if(configurationSection == null)return null;
        // Load required fields
        Material material = Material.getMaterial(configurationSection.getString("material", "STONE").toUpperCase());
        String displayName = configurationSection.getString("displayName", "&fDefault Item");
        int amount = configurationSection.getInt("amount", 1);
        List<String> loreList = configurationSection.getStringList("lore");
        KammounItem optionItem;
        if(configurationSection.getInt("slot",-1) == -1){
            optionItem = new KammounItem(material, displayName, amount, (ArrayList<String>) loreList);
        }else{
             optionItem = new KammounItem(configurationSection.getInt("slot"),material, displayName, amount, (ArrayList<String>) loreList);
        }

        // Optional fields
        optionItem.setTextureURL(configurationSection.getString("textureURL", ""));

        // Load enchantments
        if (configurationSection.isList("enchantments")) {
            List<String> enchantmentNames = configurationSection.getStringList("enchantments");
            ArrayList<Enchantment> enchantments = new ArrayList<>();
            for (String enchantmentName : enchantmentNames) {
                Enchantment enchantment = Enchantment.getByName(enchantmentName.toUpperCase());
                if (enchantment != null) {
                    enchantments.add(enchantment);
                }
            }
            optionItem.setEnchantments(enchantments);
        }

        // Load item flags
        if (configurationSection.isList("itemFlags")) {
            List<String> flagNames = configurationSection.getStringList("itemFlags");
            ArrayList<ItemFlag> itemFlags = new ArrayList<>();
            for (String flagName : flagNames) {
                try {
                    itemFlags.add(ItemFlag.valueOf(flagName.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // Log invalid flags
                }
            }
            optionItem.setItemFlags(itemFlags);
        }

        // Set glow
        optionItem.setGlow(configurationSection.getBoolean("glow", false));

        // Set durability
        int durability = configurationSection.getInt("durability", 0);
        if (durability > 0) {
            optionItem.setDurability(durability);
        }

        // Handle banner patterns
        if (material.name().endsWith("BANNER") && configurationSection.isList("bannerPatterns")) {
            List<String> patternStrings = configurationSection.getStringList("bannerPatterns");
            ArrayList<Pattern> patterns = new ArrayList<>();
            for (String patternString : patternStrings) {
                String[] parts = patternString.split("_");
                if (parts.length == 2) {
                    try {
                        DyeColor color = DyeColor.valueOf(parts[0].toUpperCase());
                        PatternType type = PatternType.valueOf(parts[1].toUpperCase());
                        patterns.add(new Pattern(color, type));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace(); // Log invalid pattern configurations
                    }
                }
            }

            // Set BannerMeta with patterns
            optionItem.setBannerMetaPattern(patterns);
        }

        return optionItem;
    }

}
