package com.kammoun.HDuels.Managers;

import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final Core core;
    private final ConcurrentHashMap<UUID, DPlayer> PlayersMap = new ConcurrentHashMap<UUID, DPlayer>();
    public PlayerManager(Core core) {
        this.core = core;
    }


    public void CreatePlayer(Player player){
        new BukkitRunnable(){

            @Override
            public void run() {
                PlayersMap.put(player.getUniqueId(), core.getMySQLManager().getPlayerTable().getDPlayer(player));
            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public DPlayer getPlayer(Player p) {
        return PlayersMap.get(p.getUniqueId());
    }
    public void reCreatePlayers(){
        for(Player pls: Bukkit.getOnlinePlayers()){
            CreatePlayer(pls);
        }
    }

    public List<DPlayer> getOnlineDPlayersList() {
        return PlayersMap.values().stream().toList();
    }

    public void removePlayer(UUID uuid) {
        if(uuid == null)return;
        PlayersMap.remove(uuid);
    }
}
