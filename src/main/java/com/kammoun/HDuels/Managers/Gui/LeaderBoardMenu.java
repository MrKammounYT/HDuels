package com.kammoun.HDuels.Managers.Gui;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Data.LeaderboardInfoHolder;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.Inventory.LeaderBoardInvHolder;
import com.kammoun.HDuels.Utils.Others.RankUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LeaderBoardMenu {

    private final Core core;


    public LeaderBoardMenu(Core core) {
        this.core = core;
    }

    public void open(Player p, int page) {
        core.getMySQLManager().getPlayerTable().getLeaderboardsPlayersAsync(core,page).thenApply(info -> {
            Inventory inventory = Bukkit.createInventory(new LeaderBoardInvHolder(page, info.size() < 45), 54, ChatFormater.Color("            ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ"));
            int i = 0;
            for (LeaderboardInfoHolder info1 : info) {
                inventory.setItem(i, getLeaderBoardItem(info1, i + 1));
                i++;
            }
            for (int j = 0; j < 9; j++) {
                inventory.setItem(45 + j, Filler());
            }
            inventory.setItem(48, PreviousPage());
            inventory.setItem(50, nextPage());

            return inventory;
        }).thenAccept(inventory -> {
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> p.openInventory(inventory));
        }).exceptionally(ex -> {
            ex.printStackTrace();
            openFallbackInventory(p);
            return null;
        });
    }

    private void openFallbackInventory(Player p) {
        // Create a simple fallback inventory
        Inventory fallbackInventory = Bukkit.createInventory(new LeaderBoardInvHolder(0, true), 54, ChatFormater.Color("            ʟᴇᴀᴅᴇʀʙᴏᴀʀᴅ"));

        // Add a message item to the fallback inventory
        ItemStack errorItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = errorItem.getItemMeta();
        meta.setDisplayName(ChatFormater.Color("&cError Loading Leaderboard"));
        List<String> lore = new ArrayList<>();
        lore.add(ChatFormater.Color("&7An error occurred while loading the leaderboard."));
        lore.add(ChatFormater.Color("&7Please try again later."));
        meta.setLore(lore);
        errorItem.setItemMeta(meta);

        fallbackInventory.setItem(22, errorItem);

        // Open the fallback inventory for the player
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> p.openInventory(fallbackInventory));
    }


    private ItemStack getLeaderBoardItem(LeaderboardInfoHolder leaderboardInfoHolder,int position) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        if (leaderboardInfoHolder.getUuid() != null) {
            try {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(leaderboardInfoHolder.getUuid());
                if (offlinePlayer.getName() != null && !offlinePlayer.getName().startsWith(".")) {
                    skullMeta.setOwningPlayer(offlinePlayer);
                }
            } catch (Exception e) {
                itemStack.setType(Material.PLAYER_HEAD);
            }
        }
        skullMeta.setDisplayName(ChatFormater.Color("&#93c6bfᴛᴏᴘ #"+position));
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatFormater.Color("&8ᴅᴜᴇʟ ʀᴀɴᴋɪɴɢ "));
        lore.add(" ");
        lore.add(ChatFormater.Color("&#5bdecc&lPLAYER STATS"));
        lore.add(ChatFormater.Color("&#93c6bf| &fᴘʟᴀʏᴇʀ ɴᴀᴍᴇ: &#93c6bf" + leaderboardInfoHolder.getPlayerName()));
        lore.add(ChatFormater.Color("&#93c6bf| &fᴘʟᴀʏᴇʀ ʀᴀɴᴋ: &#93c6bf" + RankUtils.getPlayerRank(leaderboardInfoHolder.getPoints()).getNonColoredFancyName()));
        lore.add(ChatFormater.Color("&#93c6bf| &fᴘʟᴀʏᴇʀ ʟᴘ: &#93c6bf" + leaderboardInfoHolder.getPoints()));
        skullMeta.setLore(lore);
        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
    private ItemStack nextPage(){
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatFormater.Color("&eNext Page"));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    private ItemStack PreviousPage(){
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatFormater.Color("&ePrevious Page"));
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    private ItemStack Filler(){
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}