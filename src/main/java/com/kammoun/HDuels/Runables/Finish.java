package com.kammoun.HDuels.Runables;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Finish extends BukkitRunnable {

    private int timer = 10;
    private final HDuel duel;

    public Finish(HDuel duel) {
        this.duel = duel;
    }
    @Override
    public void run() {
        if(timer == 0){
            duel.reset();
            cancel();
        }

        timer--;
    }
}
