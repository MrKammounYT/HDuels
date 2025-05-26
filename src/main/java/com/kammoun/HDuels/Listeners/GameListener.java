package com.kammoun.HDuels.Listeners;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Commands.Player.Duel;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.HDuel;
import com.kammoun.HDuels.Utils.Others.LocationAPI;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Objects;
import java.util.Random;

public class GameListener implements Listener {

    private final Core core;
    private final Random random = new Random();

    public GameListener(Core core) {
        this.core = core;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e){
        if(core.getPlayerManager().getPlayer(e.getPlayer())!= null && core.getPlayerManager().getPlayer(e.getPlayer()).isInDuel()){
            HDuel hDuel = core.getPlayerManager().getPlayer(e.getPlayer()).getPlayerCurrentDuel();
            if(hDuel.getDuelArena().getArenaState() != GameState.ITEMCOLLECT)return;
            core.getPlayerManager().getPlayer(e.getPlayer()).setPlayerCurrentDuel(null);
            hDuel.getDuelsPlayers().remove(e.getPlayer().getUniqueId());

        }
    }



    @EventHandler
    public void onDuelStartingMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (isInDuel(p)) {
            HDuel duel = core.getPlayerManager().getPlayer(p).getPlayerCurrentDuel();
            if (duel.getDuelArena().getArenaState().equals(GameState.STARTING)) {
                if (!e.getFrom().toVector().equals(e.getTo().toVector())) {
                    e.setCancelled(true);
                    p.teleport(e.getFrom());
                }
            }
        }
    }
    @EventHandler
    public void onPlayerChorusFruitEat(PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.CHORUS_FRUIT) {
            if (isInDuel(e.getPlayer())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTotemPop(EntityResurrectEvent e) {
        if(!(e.getEntity() instanceof Player))return;
        if (isInDuel((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandWrite(PlayerCommandPreprocessEvent e){
        if(e.getMessage().equals("/leave") ||e.getMessage().equals("leave")
        ||e.getMessage().equals("/collect") ||e.getMessage().equals("collect"))return;
        if(isInDuel(e.getPlayer())){
            e.getPlayer().sendMessage(ChatFormater.Color("&cYou can't use this command while in duel!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDuelLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(isInDuel(p)){
            getPlayerDuel(p).eliminatePlayer(p,core.getPlayerManager().getPlayer(p));
        }
        core.getMySQLManager().savePlayerData(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleportToDuelPlayer(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        Location targetLocation = e.getTo();
        if(player.hasPermission("hiber.admin"))return;
        if(core.getQueueManager().isInQueue(player))return;
        if(isInDuel(player))return;
        if(targetLocation.getWorld().getName().equals("tutorialworld")){
            e.setCancelled(true);
            return;
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        if(isInDuel(p)){
            p.getWorld().spawnParticle(Particle.TRIAL_OMEN, p.getLocation(), 30, 0.5, 0.5, 0.5, 0.1);
            getPlayerDuel(p).eliminatePlayer(p,core.getPlayerManager().getPlayer(p));
            e.getDrops().clear();
        }
    }

    private HDuel getPlayerDuel(Player p){
        return core.getPlayerManager().getPlayer(p).getPlayerCurrentDuel();
    }

    private boolean isInDuel(Player p){
        if(core == null) return false;
        if(core.getPlayerManager() == null) return false;
        if(core.getPlayerManager().getPlayer(p) == null) return false;
        DPlayer dPlayer = core.getPlayerManager().getPlayer(p);
        return dPlayer.isInDuel();
    }
}
