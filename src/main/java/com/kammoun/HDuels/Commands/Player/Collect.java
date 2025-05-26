package com.kammoun.HDuels.Commands.Player;

import com.kammoun.HDuels.API.Commands.KCommand;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelLootHolder;
import com.kammoun.HDuels.Utils.Others.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Collect extends KCommand implements Listener {
    public Collect(Core core, String permissions) {
        super(core, permissions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!IsAPlayer(commandSender))return true;
        Player p = (Player) commandSender;
        if(!isInDuel(p)) {
            return true;
        }
        HDuel duel = core.getPlayerManager().getPlayer(p).getPlayerCurrentDuel();
        if(!duel.getDuelArena().getArenaState().equals(GameState.ITEMCOLLECT))return true;
        duel.openLootCollectInventory(p);
        return super.onCommand(commandSender, command, s, args);
    }


    private boolean isInDuel(Player p){
        if(core == null) return false;
        if(core.getPlayerManager() == null) return false;
        return core.getPlayerManager().getPlayer(p) != null && core.getPlayerManager().getPlayer(p).isInDuel();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
