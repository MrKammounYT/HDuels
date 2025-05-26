package com.kammoun.HDuels.API.Commands;


import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Managers.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class KCommand implements CommandExecutor, TabCompleter {

    public final Core core;
    private final List<String> permissions;

    public KCommand(Core core,List<String> permissions) {
        this.core = core;
        this.permissions = permissions;
    }
    public KCommand(Core core,String permissions) {
        this.core = core;
        if(permissions == null) {
            this.permissions = List.of();
        }else{
            this.permissions = List.of(permissions);
        }
    }

    public Player getPlayer(CommandSender commandSender) {
        return (Player) commandSender;
    }

    public boolean IsAPlayer(CommandSender sender) {
        return sender instanceof org.bukkit.entity.Player;
    }

    public boolean hasPermission(CommandSender sender) {
        if(!IsAPlayer(sender)) return true;
        for(String permission : permissions) {
            if(sender.hasPermission(permission)) {
                return true;
            }
        }
        return permissions.isEmpty();
    }

    public void message(CommandSender sender, String message) {
        sender.sendMessage( ChatFormater.Color(message));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        return true;
    }


}
