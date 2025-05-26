package com.kammoun.HDuels.API.Utils.Holders;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Skulls.SkullHandler;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KammounItem {

    private final Material material;
    private final String displayName;
    private final ArrayList<String> lore;
    private final int amount;
    private int durability;
    private List<Pattern> bannerMetaPattern;
    private String TextureURL;
    private ArrayList<Enchantment> Enchantments;
    private ArrayList<ItemFlag> itemFlags;
    private boolean Glow;
    private int slot;

    public KammounItem(Material material, String displayName, int amount, ArrayList<String> lore) {
        this.material = material;
        this.displayName = displayName;
        this.amount = amount;
        this.lore = lore;
    }
    public KammounItem(int slot,Material material, String displayName, int amount, ArrayList<String> lore) {
        this.slot = slot;
        this.material = material;
        this.displayName = displayName;
        this.amount = amount;
        this.lore = lore;
    }

    public int getSlot() {
        return slot;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public String getTextureURL() {
        return TextureURL;
    }

    public ArrayList<Enchantment> getEnchantments() {
        return Enchantments;
    }

    public ArrayList<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    public boolean isGlow() {
        return Glow;
    }

    public void setBannerMetaPattern(List<Pattern> bannerMetaPattern) {
        this.bannerMetaPattern = bannerMetaPattern;
    }

    public void setTextureURL(String textureURL) {
        TextureURL = textureURL;
    }

    public void setEnchantments(ArrayList<Enchantment> enchantments) {
        Enchantments = enchantments;
    }

    public void setItemFlags(ArrayList<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public void setGlow(boolean glow) {
        Glow = glow;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }
    public ItemStack getItem() {
        ItemStack itemStack = new ItemStack(material, amount);
        if (TextureURL != null && !TextureURL.isEmpty()) {
            try {
                itemStack = SkullHandler.getCustomSkull(TextureURL);
                itemStack.setAmount(amount);
            } catch (Exception e) {
                e.printStackTrace();
                itemStack = new ItemStack(material, amount);
            }
        }
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatFormater.Color(displayName));

            if (lore != null && !lore.isEmpty()) {
                meta.setLore(lore.stream().map(ChatFormater::Color).collect(Collectors.toList()));
            }

            if (material.getMaxDurability() > 0 && durability > 0) {
                itemStack.setDurability((short) durability);
            }
            if (Enchantments != null) {
                for (Enchantment enchantment : Enchantments) {
                    meta.addEnchant(enchantment, 1, true);
                }
            }

            if (itemFlags != null) {
                meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            }

            if (Glow) {
                meta.addEnchant(Enchantment.SWEEPING_EDGE, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            if (bannerMetaPattern != null && meta instanceof BannerMeta) {
                BannerMeta bannerMetaCast = (BannerMeta) meta;
                bannerMetaCast.setPatterns(bannerMetaPattern);
            }

            itemStack.setItemMeta(meta);
        }

        return itemStack;
    }



}
