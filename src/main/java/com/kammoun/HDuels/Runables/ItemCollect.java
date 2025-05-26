package com.kammoun.HDuels.Runables;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Others.LocationAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ItemCollect extends BukkitRunnable {

    private int timer = 180; // 3 minutes in seconds
    private final HDuel duel;

    public ItemCollect(HDuel duel,boolean draw) {
        this.duel = duel;
        if(draw)timer=0;
    }

    @Override
    public void run() {
        if (timer == 0 || duel.getDuelsPlayers().isEmpty()) {
            Finish finish = new Finish(duel);
            finish.runTaskTimer(Main.getInstance(),0, 20L);
            for(UUID players : duel.getDuelsPlayers()){
                if(Bukkit.getPlayer(players) == null) continue;
                if(LocationAPI.getLocation("spawn") == null) break;
                Bukkit.getPlayer(players).teleport(LocationAPI.getLocation("spawn"));
                duel.getDuelsPlayers().remove(players);
                if(duel.getDPlayer1() !=null){
                    duel.getDPlayer1().setPlayerCurrentDuel(null);
                }
                if(duel.getDPlayer2() !=null){
                    duel.getDPlayer2().setPlayerCurrentDuel(null);
                }
            }
            cancel();
            return;
        }
        String formattedTime = formatTime(timer);
        String actionBarMessage = ChatFormater.Color("&7Type &#ff0000/collect&7 to get your loot. Then &#ff0000/leave&7 to leave.Time to collect: &#ff0000" + formattedTime);
        duel.getDuelsPlayers().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) sendActionBar(player, actionBarMessage);
        });

        timer--;

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
