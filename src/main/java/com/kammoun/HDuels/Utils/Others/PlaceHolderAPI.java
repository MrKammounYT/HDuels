package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlaceHolderAPI extends PlaceholderExpansion {

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null; // Return null if the player is not provided
        }

        DPlayer dPlayer = Main.getInstance().getCore().getPlayerManager().getPlayer(player);
        if (dPlayer == null) {
            return null;
        }

        Core core = Main.getInstance().getCore();

        switch (params) {
            case "queued_players":
                if (core != null && core.getQueueManager() != null) {
                    return String.valueOf(core.getQueueManager().getPLAYERS_IN_QUEUE().size());
                }
                return "0";
            case "player_name":
                return dPlayer.getName();

            case "player_points":
                return String.valueOf(dPlayer.getPoints());

            case "player_kills":
                return String.valueOf(dPlayer.getKills());

            case "player_games_played":
                return String.valueOf(dPlayer.getGamesPlayed());

            case "player_losses":
                return String.valueOf(dPlayer.getLosses());

            case "player_deaths":
                return String.valueOf(dPlayer.getDeaths());

            case "player_wins":
                return String.valueOf(dPlayer.getWins());

            case "player_win_rate":
                return String.format("%.2f", dPlayer.getWinRate());

            case "player_rank":
                return ChatFormater.Color(dPlayer.getRank());

            case "player_kd":
                return String.format("%.2f", dPlayer.getKD());

            default:
                return null;
        }
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hduel";
    }

    @Override
    public @NotNull String getAuthor() {
        return "kammoun";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
}
