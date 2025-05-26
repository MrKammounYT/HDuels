package com.kammoun.HDuels.Listeners.Duel;

import com.kammoun.HDuels.Events.PlayerRankUpEvent;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import com.kammoun.HDuels.Utils.Others.RankUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRankUP implements Listener {
    
    private final Core core;
    
    public PlayerRankUP(Core core) {
        this.core = core;
    }
    @EventHandler
    public void onPlayerRankUp(PlayerRankUpEvent event) {
    }
}
