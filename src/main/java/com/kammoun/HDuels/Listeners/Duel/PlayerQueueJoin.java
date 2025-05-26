package com.kammoun.HDuels.Listeners.Duel;

import com.kammoun.HDuels.Events.PlayerQueueJoinEvent;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQueueJoin implements Listener {

    private final Core core;
    public PlayerQueueJoin(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerJoinQueue(PlayerQueueJoinEvent e) {
        Player p = e.getPlayer();
        if(core.getQueueManager().isInQueue(p))return;
        MessageManager.sendDuelQueueJoin(p);
        core.getQueueManager().addPlayerToQueue(p);
        core.getSongsManager().playSong(p);
    }



}
