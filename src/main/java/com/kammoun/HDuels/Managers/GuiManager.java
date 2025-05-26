package com.kammoun.HDuels.Managers;

import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Gui.DuelMenuLoader;
import com.kammoun.HDuels.Managers.Gui.LeaderBoardMenu;
import com.kammoun.HDuels.Managers.Gui.RewardManager;
import com.kammoun.HDuels.Managers.Gui.StatsMenuLoader;

public class GuiManager {


    private final DuelMenuLoader duelMenuLoader;
    private final StatsMenuLoader statsMenuLoader;
    private final RewardManager rewardManager;
    private final LeaderBoardMenu leaderBoardMenu;

    public GuiManager(Core core, Main main) {
        this.duelMenuLoader = new DuelMenuLoader(core,main,"Gui/DuelGui.yml");
        this.statsMenuLoader = new StatsMenuLoader(main,"Gui/StatsGui.yml");
        this.rewardManager = new RewardManager(main, core);
        this.leaderBoardMenu = new LeaderBoardMenu(core);
    }

    public LeaderBoardMenu getLeaderBoardMenu() {
        return leaderBoardMenu;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public DuelMenuLoader getDuelMenuLoader() {
        return duelMenuLoader;
    }

    public StatsMenuLoader getStatsMenuLoader() {
        return statsMenuLoader;
    }

}
