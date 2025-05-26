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

public class Leave extends KCommand {

    public Leave(Core core, String permissions) {
        super(core, permissions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!IsAPlayer(commandSender))return true;
        Player p = getPlayer(commandSender);
        DPlayer dPlayer = core.getPlayerManager().getPlayer(p);
        if(dPlayer.isInDuel()){
            dPlayer.getPlayerCurrentDuel().eliminatePlayer(p,dPlayer);
        }
        return super.onCommand(commandSender, command, s, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}

