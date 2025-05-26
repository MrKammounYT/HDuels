package com.kammoun.HDuels.Managers;

import com.kammoun.HDuels.API.KammounCore;
import com.kammoun.HDuels.Events.PlayerQueueLeaveEvent;
import com.kammoun.HDuels.Listeners.*;
import com.kammoun.HDuels.Listeners.Duel.*;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Configurations.ArenaManager;
import com.kammoun.HDuels.Managers.Configurations.RankRewardSystem;
import com.kammoun.HDuels.Managers.Data.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import static com.kammoun.HDuels.Main.Inform;

public class Core implements KammounCore {


    private ArenaManager arenaManager;
    private PlayerManager playerManager;
    private QueueManager queueManager;
    private DuelManager duelManager;
    private GuiManager guiManager;
    private MySQLManager mySQLManager;
    private RankRewardSystem rankRewardSystem;
    private SongsManager songsManager;
    public Core(Main main) {
        Corelogic(main);
        registerEvents(main);

    }

    @Override
    public void Corelogic(Main main) {
        // TODO: Implement core logic here.
        this.songsManager = new SongsManager(main);
        this.arenaManager = new ArenaManager(main);
        this.mySQLManager = new MySQLManager(main,this);
        this.playerManager = new PlayerManager(this);
        this.queueManager = new QueueManager(this);
        this.duelManager = new DuelManager(this);
        this.guiManager = new GuiManager(this,main);
        this.rankRewardSystem = new RankRewardSystem(main);
        playerManager.reCreatePlayers();
        Inform("&e&m----------------------------------------------------------------");
    }

    @Override
    public void reloadPlugin(Main plugin) {
        mySQLManager.saveDataOnDisable();
        songsManager.stopSongsForAllPlayers();
        Corelogic(plugin);
        plugin.reloadMain();
    }

    @Override
    public void registerEvents(Main plugin) {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerJoinAndQuit(this),plugin);
        pm.registerEvents(new GameListener(this), plugin);
        pm.registerEvents(new DuelQueueInventory(this),plugin);
        pm.registerEvents(new LootCollectListener(this), plugin);
        pm.registerEvents(new PlayerQueueLeave(this),plugin);
        pm.registerEvents(new PlayerQueueJoin(this),plugin);
        pm.registerEvents(new PlayerPointsUpdate(this),plugin);
        pm.registerEvents(new PlayerDuelWin(this),plugin);
        pm.registerEvents(new PlayerRankUP(this),plugin);
        pm.registerEvents(new RewardCollect(this),plugin);
        pm.registerEvents(new LeaderBoardInventory(this),plugin);
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public MySQLManager getMySQLManager() {
        return mySQLManager;
    }

    public QueueManager getQueueManager() {
        return queueManager;
    }

    public SongsManager getSongsManager() {
        return songsManager;
    }

    public RankRewardSystem getRankRewardSystem() {
        return rankRewardSystem;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }


}
