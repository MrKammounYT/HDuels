package com.kammoun.HDuels.Managers;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class QueueManager {

    private final ConcurrentHashMap<String, CopyOnWriteArrayList<UUID>> PLAYERS_IN_QUEUE = new ConcurrentHashMap<>();
    private final Core core;

    public QueueManager(Core core) {
        this.core = core;
    }

    public void addPlayerToQueue(Player p) {
        if (isInQueue(p)) {
            p.sendMessage("§cYou are already in the queue.");
            return;
        }

        DPlayer dPlayer = core.getPlayerManager().getPlayer(p);
        if (dPlayer == null) {
            p.sendMessage("§cAn error occurred. Please try again.");
            return;
        }

        PLAYERS_IN_QUEUE.computeIfAbsent(dPlayer.getRank(), k -> new CopyOnWriteArrayList<>());
        PLAYERS_IN_QUEUE.get(dPlayer.getRank()).add(p.getUniqueId());
        new BukkitRunnable(){

            @Override
            public void run() {
                if(!p.isOnline() || !isInQueue(p)){
                    cancel();
                }
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(ChatFormater.Color("&7You are now in the duel queue")));
            }
        }.runTaskTimer(Main.getInstance(),0,20L);
        new BukkitRunnable() {
            @Override
            public void run() {
                checkQueue();
            }
        }.runTaskLater(Main.getInstance(), 20 * 5L);
    }

    private void checkQueue() {
        for (String rank : PLAYERS_IN_QUEUE.keySet()) {
            CopyOnWriteArrayList<UUID> queue = PLAYERS_IN_QUEUE.get(rank);
            while (queue.size() >= 2) {
                UUID player1UUID = queue.remove(0);
                UUID player2UUID = queue.remove(0);

                // Check if players are in the same team
                /*Team team1 = Team.getTeam(player1UUID);
                Team team2 = Team.getTeam(player2UUID);

                if (team1 != null && team2 != null && team1.getName().equals(team2.getName())) {
                    queue.add(player1UUID);
                    queue.add(player2UUID);
                    continue; // Skip to the next iteration
                }*/
                // Get Player objects from UUIDs
                Player player1 = Bukkit.getPlayer(player1UUID);
                Player player2 = Bukkit.getPlayer(player2UUID);
                if (player1 != null && player2 != null) {
                    PreparePlayerForCombat(player1);
                    PreparePlayerForCombat(player2);
                    core.getDuelManager().startDuel(player1, player2);
                } else {
                    if (player1 != null) {
                        player1.sendMessage("§cMatch failed - opponent disconnected");
                    }
                    if (player2 != null) {
                        player2.sendMessage("§cMatch failed - opponent disconnected");
                    }
                    leaveQueue(player1);
                    leaveQueue(player2);
                }
            }
        }
    }

    public void leaveQueue(Player p) {
        if(p == null)return;
        UUID playerUUID = p.getUniqueId();
        core.getSongsManager().stopSong(p);
        PLAYERS_IN_QUEUE.values().removeIf(queue -> queue.remove(playerUUID));
    }

    public boolean isInQueue(Player p) {
        UUID playerUUID = p.getUniqueId();
        for (CopyOnWriteArrayList<UUID> queuePlayers : PLAYERS_IN_QUEUE.values()) {
            if (queuePlayers.contains(playerUUID)) {
                return true;
            }
        }
        return false;
    }


    public ConcurrentHashMap<String, CopyOnWriteArrayList<UUID>> getPLAYERS_IN_QUEUE() {
        return PLAYERS_IN_QUEUE;
    }

    private void PreparePlayerForCombat(Player p) {
        if (p == null) return;
        core.getSongsManager().stopSong(p);
        p.setGameMode(GameMode.SURVIVAL);
        p.setHealth(20.0);
        p.setFoodLevel(20);
        p.setAllowFlight(false);
        for (PotionEffect effect : new ArrayList<>(p.getActivePotionEffects())) {
            p.removePotionEffect(effect.getType());
        }
    }
}