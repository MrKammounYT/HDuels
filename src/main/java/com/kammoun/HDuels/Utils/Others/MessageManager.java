package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class MessageManager {

    private static YamlConfiguration langConfig;

    // Load the lang.yml file
    public void loadMessages(Main plugin) {
        File langFile = new File(plugin.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false); // Save the default lang.yml if it doesn't exist
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    // Helper method to send formatted messages from the config
    private static void sendMessages(Player player, String path, String... replacements) {
        if (player == null || !langConfig.contains(path)) return;

        List<String> messages = langConfig.getStringList(path);
        for (String message : messages) {
            // Replace placeholders
            for (int i = 0; i < replacements.length; i += 2) {
                message = message.replace(replacements[i], replacements[i + 1]);
            }
            player.sendMessage(ChatFormater.Color(PlaceHolderManager.ReplacePlaceHolders(player,message)));
        }
    }

    public static void sendDuelFoundMessage(Player p,String opponentName,HDuel hDuel) {
        sendMessages(p, "duel_found",
                "%map%", hDuel.getDuelArena().getName(),
                "%opponent%", opponentName,
                "%ping%", String.valueOf(p.getPing()));
    }

    public static void sendDuelQueueJoin(Player p) {
        sendMessages(p, "queue_join",
                "%ping%", String.valueOf(p.getPing()));
    }

    public static void sendDuelQueueLeave(Player p) {
        sendMessages(p, "queue_leave");
    }

    public static void sendDuelWinMessage(Player p, String winnerName, String loserName) {
        sendMessages(p, "duel_win",
                "%winner%", winnerName,
                "%loser%", loserName);
    }

    public static void sendDuelDraw(Player p) {
        sendMessages(p, "duel_draw");
    }

    public static void sendPointLost(Player p, int amount) {
        sendMessages(p, "point_lost",
                "%amount%", String.valueOf(amount));
    }

    public static void sendPointWon(Player p, int amount) {
        sendMessages(p, "point_won",
                "%amount%", String.valueOf(amount));
    }
    public static void sendPlayerRankUp(Player p, DPlayer dPlayer) {
        sendMessages(p, "Player_Rankup","%rank_name%", RankUtils.getPlayerRank(dPlayer.getPoint()).getFancyName(),
                "%player_name%",p.getName());
    }
    public static void sendPlayerRankDown(Player p, DPlayer dPlayer) {
        sendMessages(p, "Player_DeRank","%rank_name%", RankUtils.getPlayerRank(dPlayer.getPoint()).getFancyName(),
                "%player_name%",p.getName());
    }
}
