package com.kammoun.HDuels.Events;

import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerDuelWinEvent extends Event {

    private final DPlayer Winner;
    private final HDuel hDuel;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerDuelWinEvent(HDuel hDuel,DPlayer Winner) {
        this.isCancelled = false;
        this.Winner = Winner;
        this.hDuel = hDuel;
    }

    public DPlayer getWinner() {
        return Winner;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public HDuel getHDuel() {
        return hDuel;
    }

    public Player getWiningPlayer() {
        return Bukkit.getPlayer(Winner.getUuid());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
