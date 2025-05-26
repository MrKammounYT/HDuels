package com.kammoun.HDuels.Commands.Player;

import com.kammoun.HDuels.API.Commands.KCommand;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Duel extends KCommand {
    public Duel(Core core, String permissions) {
        super(core, permissions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        // Check if the command sender is a player
        if (!IsAPlayer(commandSender)) {
            message(commandSender, "&cThis command can only be used by players!");
            return true; // Return true to indicate the command was handled (even though it failed)
        }

        Player p = getPlayer(commandSender);
        DPlayer dPlayer = core.getPlayerManager().getPlayer(p);
        // Check if the player data is loaded
        if (dPlayer == null) {
            message(commandSender, "&cCannot use this command. Please rejoin the server!");
            return true;
        }

        if (dPlayer.isInDuel())return true;

        if (args.length == 1) {
            String cmd = args[0];
            if (cmd.equalsIgnoreCase("leaderboard")) {
                core.getGuiManager().getLeaderBoardMenu().open(p, 1);
                return true; // Command was handled successfully
            }
            return true;
        }
        if (args.length == 0) {
            core.getGuiManager().getDuelMenuLoader().openInventory(p);
            return true; // Command was handled successfully
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1){
            return List.of("leaderboard");
        }
        return List.of();
    }
}
