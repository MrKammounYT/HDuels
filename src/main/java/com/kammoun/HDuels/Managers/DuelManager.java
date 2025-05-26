package com.kammoun.HDuels.Managers;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Runables.Starting;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.Arena;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class DuelManager {
    private final Core core;

    public DuelManager(Core core) {
        this.core = core;
    }

    public void startDuel(Player player1, Player player2) {
        Arena arena = core.getArenaManager().getAvailableArena();
        if(arena == null) {
            player1.sendMessage(ChatFormater.Color("&cDuel was canceled because there is no available arena!"));
            player2.sendMessage(ChatFormater.Color("&cDuel was canceled because there is no available arena!"));
            return;
        }
        arena.setArenaState(GameState.STARTING);
        DPlayer dPlayer1 = core.getPlayerManager().getPlayer(player1);
        DPlayer dPlayer2 = core.getPlayerManager().getPlayer(player2);
        HDuel hDuel = new HDuel(arena,dPlayer1,dPlayer2);
        dPlayer1.setPlayerCurrentDuel(hDuel);
        dPlayer2.setPlayerCurrentDuel(hDuel);
        MessageManager.sendDuelFoundMessage(player1,player2.getName(),hDuel);
        MessageManager.sendDuelFoundMessage(player2,player1.getName(),hDuel);
        new BukkitRunnable(){

            @Override
            public void run() {
                player1.teleport(arena.getSpawn1());
                player2.teleport(arena.getSpawn2());
            }
        }.runTaskLater(Main.getInstance(),10L);
        Starting starting = new Starting(hDuel);
        starting.runTaskTimer(Main.getInstance(),0,20L);
    }



}