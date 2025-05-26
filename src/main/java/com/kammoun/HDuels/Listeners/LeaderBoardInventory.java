package com.kammoun.HDuels.Listeners;

import com.kammoun.HDuels.API.Inventory.KMenus;
import com.kammoun.HDuels.Data.LeaderboardInfoHolder;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelInventoryHolder;
import com.kammoun.HDuels.Utils.Holder.Inventory.LeaderBoardInvHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class LeaderBoardInventory extends KMenus {
    public LeaderBoardInventory(Core coreManager) {
        super(coreManager);
    }
    @EventHandler
    public void onLeaderboardInv(InventoryClickEvent event) {
        if(!isValidInventoryHolder(event.getInventory()))return;
        if(!(event.getInventory().getHolder() instanceof LeaderBoardInvHolder))return;
        event.setCancelled(true);
        if(!isValidItem(event.getCurrentItem()))return;
        Player player = (Player) event.getWhoClicked();
        LeaderBoardInvHolder leaderboardInvHolder = (LeaderBoardInvHolder) event.getInventory().getHolder();
        if(hasDisplayName(event.getCurrentItem(),"&eNext Page")){
            if(leaderboardInvHolder.isMaxPage())return;
            core.getGuiManager().getLeaderBoardMenu().open(player,leaderboardInvHolder.getCurrentPage()+1);
            return;
        }
        if(hasDisplayName(event.getCurrentItem(),"&ePrevious Page")){
            if(leaderboardInvHolder.getCurrentPage() <= 1)return;
            core.getGuiManager().getLeaderBoardMenu().open(player,leaderboardInvHolder.getCurrentPage()-1);
            return;
        }


    }
}
