package com.kammoun.HDuels.Runables;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Events.PlayerDuelWinEvent;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;
import java.util.UUID;

public class InGame extends BukkitRunnable {

    private int timer = 300;
    private final HDuel duel;
    private final Random random = new Random();

    public InGame(HDuel duel) {
        this.duel = duel;
    }

    @Override
    public void run() {
        if (duel.getDuelsPlayers().size() == 1) {
            Player winner = Bukkit.getPlayer(duel.getDuelsPlayers().getFirst());
            if(winner != null){
                Bukkit.getPluginManager().callEvent(new PlayerDuelWinEvent(duel,Main.getInstance().getCore().getPlayerManager().getPlayer(winner)));
            }
            stop(false);
            return;
        }
        if (timer == 0 || duel.getDuelsPlayers().isEmpty()) {
            MessageManager.sendDuelDraw(duel.getPlayer1());
            MessageManager.sendDuelDraw(duel.getPlayer2());
            stop(true);
            return;
        }
        String formattedTime = formatTime(timer);
        String actionBarMessage = ChatFormater.Color("&7Time Left: &#ff0000" + formattedTime);
        duel.getDuelsPlayers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) sendActionBar(player, actionBarMessage);
        });

        timer--;
    }

    private void stop(boolean draw){
        ItemCollect itemCollect = new ItemCollect(duel,draw);
        itemCollect.runTaskTimer(Main.getInstance(),0,20L);
        duel.getDuelArena().setArenaState(GameState.ITEMCOLLECT);
        cancel();
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }




    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}