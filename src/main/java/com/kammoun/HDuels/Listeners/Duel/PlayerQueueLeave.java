package com.kammoun.HDuels.Listeners.Duel;

import com.kammoun.HDuels.Events.PlayerQueueJoinEvent;
import com.kammoun.HDuels.Events.PlayerQueueLeaveEvent;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQueueLeave implements Listener {
    private final Core core;
    public PlayerQueueLeave(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerJoinQueue(PlayerQueueLeaveEvent e) {
        Player p = e.getPlayer();
        core.getQueueManager().leaveQueue(p);
        core.getSongsManager().stopSong(p);
        MessageManager.sendDuelQueueLeave(p);

    }
}
