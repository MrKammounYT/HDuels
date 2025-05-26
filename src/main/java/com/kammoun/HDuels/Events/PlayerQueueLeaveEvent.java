package com.kammoun.HDuels.Events;

import com.kammoun.HDuels.Utils.Holder.DPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerQueueLeaveEvent extends Event {

    private final DPlayer dPlayer;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public PlayerQueueLeaveEvent(DPlayer dPlayer) {
        this.isCancelled = false;
        this.dPlayer = dPlayer;
    }

    public DPlayer getDPlayer() {
        return dPlayer;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(dPlayer.getUuid());
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}