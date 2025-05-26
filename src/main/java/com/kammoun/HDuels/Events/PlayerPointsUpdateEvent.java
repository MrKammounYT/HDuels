package com.kammoun.HDuels.Events;

import com.kammoun.HDuels.Utils.Holder.DPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class PlayerPointsUpdateEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private DPlayer dPlayer;
    private final int PreviousPoints;
    private final int NewPoints;

    public PlayerPointsUpdateEvent(DPlayer dPlayer, int PreviousPoints, int NewPoints) {
        this.isCancelled = false;
        this.dPlayer = dPlayer;
        this.PreviousPoints = PreviousPoints;
        this.NewPoints = NewPoints;
    }

    public DPlayer getdPlayer() {
        return dPlayer;
    }

    public int getNewPoints() {
        return NewPoints;
    }

    public int getPreviousPoints() {
        return PreviousPoints;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
    public Player getPlayer() {
        return dPlayer.getPlayer();
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
