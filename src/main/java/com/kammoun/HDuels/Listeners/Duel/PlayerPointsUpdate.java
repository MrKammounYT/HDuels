package com.kammoun.HDuels.Listeners.Duel;

import com.kammoun.HDuels.Events.PlayerPointsUpdateEvent;
import com.kammoun.HDuels.Events.PlayerQueueLeaveEvent;
import com.kammoun.HDuels.Events.PlayerRankUpEvent;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Enums.Ranks;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import com.kammoun.HDuels.Utils.Others.RankUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPointsUpdate implements Listener {

    private final Core core;

    public PlayerPointsUpdate(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerPointsUpdate(PlayerPointsUpdateEvent e) {
        Player p = e.getPlayer();
        Ranks previousRank = RankUtils.getPlayerRank(e.getPreviousPoints());
        Ranks newRank = RankUtils.getPlayerRank(e.getNewPoints());
        if (previousRank.getPriority() < newRank.getPriority()) {
            MessageManager.sendPlayerRankUp(p,e.getdPlayer());
            Bukkit.getPluginManager().callEvent(new PlayerRankUpEvent(e.getdPlayer()));
        }else if (previousRank.getPriority()> newRank.getPriority()){
            MessageManager.sendPlayerRankDown(p,e.getdPlayer());
        }
        if(!core.getQueueManager().isInQueue(e.getPlayer()))return;
        Bukkit.getPluginManager().callEvent(new PlayerQueueLeaveEvent(e.getdPlayer()));
    }
}
