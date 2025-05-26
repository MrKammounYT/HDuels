package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Dependencies.PlaceHolderAPI;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlaceHolderManager {
    private static Core core = Main.getInstance().getCore();
    public static String ReplacePlaceHolders(Player p, String messages){
        if(messages == null) return "";
        if(p == null) return messages;
        if(PlaceHolderAPI.PlaceHolderAPIExists(Main.getInstance())){
            if(PlaceholderAPI.containsPlaceholders(messages)){
                messages = PlaceholderAPI.setPlaceholders(p, messages);
            }
        }
        return messages;
    }

    public static String ReplaceOperationPlaceHolder(String message, Player player) {
        if (message == null) {
            return message;
        }

        DPlayer dPlayer = Main.getInstance().getCore().getPlayerManager().getPlayer(player);
        if (dPlayer == null) {
            return message;
        }
        if(core != null && core.getQueueManager() != null){
            message = message.replace("%hduel_queued_players%",String.valueOf(core.getQueueManager().getPLAYERS_IN_QUEUE().size()));
        }
        return message.replace("%hduel_player_name%", dPlayer.getName())
                .replace("%hduel_player_points%", String.valueOf(dPlayer.getPoints()))
                .replace("%hduel_player_kills%", String.valueOf(dPlayer.getKills()))
                .replace("%hduel_player_games_played%", String.valueOf(dPlayer.getGamesPlayed()))
                .replace("%hduel_player_losses%", String.valueOf(dPlayer.getLosses()))
                .replace("%hduel_player_deaths%", String.valueOf(dPlayer.getDeaths()))
                .replace("%hduel_player_wins%", String.valueOf(dPlayer.getWins()))
                .replace("%hduel_player_win_rate%", String.valueOf(dPlayer.getWinRate()))
                .replace("%hduel_player_rank%", ChatFormater.Color(dPlayer.getRankEnum().getFancyName()))
                .replace("%hduel_player_kd%", String.format("%.2f", dPlayer.getKD()));
    }

}
