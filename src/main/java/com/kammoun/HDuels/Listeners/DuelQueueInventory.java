package com.kammoun.HDuels.Listeners;

import com.kammoun.HDuels.API.Inventory.KMenus;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Events.PlayerQueueJoinEvent;
import com.kammoun.HDuels.Events.PlayerQueueLeaveEvent;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Managers.Gui.DuelMenuLoader;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelInventoryHolder;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import com.kammoun.HDuels.Utils.Others.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DuelQueueInventory extends KMenus {


    public DuelQueueInventory(Core coreManager) {
        super(coreManager);
    }

    @EventHandler
    public void onDuelQueue(InventoryClickEvent e){
        if(!isValidInventoryHolder(e.getInventory()))return;
        if(!(e.getInventory().getHolder() instanceof DuelInventoryHolder))return;
        Player p = (Player) e.getWhoClicked();
        e.setCancelled(true);
        if(!isValidItem(e.getCurrentItem()))return;
        DuelMenuLoader duelMenuLoader = core.getGuiManager().getDuelMenuLoader();
        if(hasDisplayName(e.getCurrentItem(),duelMenuLoader.getStats().getItem())){
            Sounds.INV_CLICK.Play(p);
            core.getGuiManager().getStatsMenuLoader().openInventory(p);
            return;
        }
        if(hasDisplayName(e.getCurrentItem(),duelMenuLoader.getRewards().getItem())){
            Sounds.INV_CLICK.Play(p);
            core.getGuiManager().getRewardManager().openRewardInventory(p);
            return;
        }
        DPlayer dPlayer = core.getPlayerManager().getPlayer(p);
        if(dPlayer == null)return;
        if(hasDisplayName(e.getCurrentItem(),duelMenuLoader.getJoinQueue().getItem())){
            Sounds.INV_CLICK.Play(p);
            Bukkit.getPluginManager().callEvent(new PlayerQueueJoinEvent(dPlayer));
            p.closeInventory();
            return;
        }
        if(hasDisplayName(e.getCurrentItem(),duelMenuLoader.getLeaveQueue().getItem())){
            Sounds.INV_CLICK.Play(p);
            Bukkit.getPluginManager().callEvent(new PlayerQueueLeaveEvent(dPlayer));
            p.closeInventory();
            return;
        }
    }
}
