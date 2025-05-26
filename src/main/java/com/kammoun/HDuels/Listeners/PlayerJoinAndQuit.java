package com.kammoun.HDuels.Listeners;

import com.kammoun.HDuels.Events.PlayerQueueLeaveEvent;
import com.kammoun.HDuels.Managers.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinAndQuit implements Listener {

    private Core core;

    public PlayerJoinAndQuit(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        core.getPlayerManager().CreatePlayer(p);
        core.getSongsManager().stopSong(p);
    }
    @EventHandler
    public void onPlayerQuit(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!core.getQueueManager().isInQueue(p))return;
        Bukkit.getPluginManager().callEvent(new PlayerQueueLeaveEvent(core.getPlayerManager().getPlayer(p)));
    }


}
