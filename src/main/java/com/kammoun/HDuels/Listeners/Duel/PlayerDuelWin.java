package com.kammoun.HDuels.Listeners.Duel;

import com.kammoun.HDuels.Events.PlayerDuelWinEvent;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Random;

public class PlayerDuelWin implements Listener {

    private final Core core;
    private final Random random = new Random();
    
    public PlayerDuelWin(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerDuelWin(PlayerDuelWinEvent event) {
        Player winner = event.getWiningPlayer();
        HDuel hDuel = event.getHDuel();
        MessageManager.sendDuelWinMessage(hDuel.getPlayer1(), winner.getName(), hDuel.getLoserName(winner));
        MessageManager.sendDuelWinMessage(hDuel.getPlayer2(), winner.getName(), hDuel.getLoserName(winner));
        DPlayer DWinner = event.getWinner();
        if(DWinner != null) {
            DWinner.addKills(1);
            DWinner.addWin();
            int PointWon =random.nextInt(21,23);
            DWinner.addPoints(PointWon);
            MessageManager.sendPointWon(winner,PointWon);
        }
    }
}
