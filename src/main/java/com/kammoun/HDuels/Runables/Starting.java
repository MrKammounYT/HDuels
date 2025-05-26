package com.kammoun.HDuels.Runables;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Starting extends BukkitRunnable {

    private final HDuel hDuel;
    private int timer = 5;

    public Starting(HDuel hDuel) {
        this.hDuel = hDuel;
    }

    @Override
    public void run() {
        if (timer == 0 || hDuel.getDuelsPlayers().size() <= 1) {
            hDuel.getDuelArena().setArenaState(GameState.INGAME);
            InGame inGame = new InGame(hDuel);
            inGame.runTaskTimer(Main.getInstance(), 0, 20L);
            this.cancel();

            // Send title and sound when the duel starts
            for (UUID uuid : hDuel.getDuelsPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendTitle(ChatFormater.Color(
                        "&#ff0000&lꜰɪɢʜᴛ ꜱᴛᴀʀᴛᴇᴅ"),
                        ChatFormater.Color("&#ff0000ɢᴏᴏᴅ ʟᴜᴄᴋ"),
                        10, 40, 10
                );
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            }
            return;
        }

        // Broadcast countdown message with color
        String countdownMessage = "&#ff0000Duel Starting in &c" + ChatColor.RED + timer;
        hDuel.sendMessage(countdownMessage);

        // Send action bar to all players
        String actionBarMessage = ChatFormater.Color("&#ff0000Starting in &c" + timer + " &#ff0000seconds");
        for (UUID uuid : hDuel.getDuelsPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;
            sendActionBar(player, actionBarMessage);
        }

        // Play a sound for each countdown tick
        for (UUID uuid : hDuel.getDuelsPlayers()) {
            Player player = Bukkit.getPlayer(uuid);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
        }

        timer--;
    }


    private void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatFormater.Color(message)));
    }
}