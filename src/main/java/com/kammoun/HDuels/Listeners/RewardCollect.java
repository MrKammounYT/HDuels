package com.kammoun.HDuels.Listeners;

import com.kammoun.HDuels.API.Inventory.KMenus;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Managers.Gui.RewardManager;
import com.kammoun.HDuels.Utils.Holder.Inventory.RewardInventoryHolder;
import com.kammoun.HDuels.Utils.Others.Sounds;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.Utils.Enums.Ranks;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.Reward;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class RewardCollect extends KMenus {

    public RewardCollect(Core coreManager) {
        super(coreManager);
    }



    @EventHandler
    public void onRewardClaimEvent(InventoryClickEvent e) {
        // Check if the inventory is valid and the holder is RewardInventoryHolder
        if (!isValidInventoryHolder(e.getInventory())) return;
        if (!(e.getInventory().getHolder() instanceof RewardInventoryHolder)) return;

        e.setCancelled(true); // Cancel the event to prevent item movement

        Player player = (Player) e.getWhoClicked();
        DPlayer dPlayer = core.getPlayerManager().getPlayer(player);
        if (dPlayer == null) return;

        RewardManager rewardManager = core.getGuiManager().getRewardManager();
        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null) return;
        // Iterate through the rewards to find the one that matches the clicked item
        for (Map.Entry<Ranks, Reward> entry : rewardManager.getRewardsMap().entrySet()) {
            Ranks rank = entry.getKey();
            Reward reward = entry.getValue();
            if (hasDisplayName(clickedItem,reward.getClaimItem().getItem())) {
                if (dPlayer.hasTakenReward(rank)) {
                    player.sendMessage(ChatFormater.Color("&cYou have already claimed this reward!"));
                    Sounds.REFUSE.Play(player);
                    player.closeInventory();
                    return;
                }
                if (!isEligibleForReward(dPlayer, rank)) {
                    player.sendMessage(ChatFormater.Color("&cYou are not eligible to claim this reward!"));
                    Sounds.REFUSE.Play(player);
                    player.closeInventory();
                    return;
                }

                // Claim the reward
                claimReward(player, dPlayer, rank, reward);
                return;
            }
        }
    }

    /**
     * Checks if the player is eligible to claim the reward for the given rank.
     *
     * @param dPlayer The player data.
     * @param rank    The rank to check.
     * @return True if the player is eligible, false otherwise.
     */
    private boolean isEligibleForReward(DPlayer dPlayer, Ranks rank) {
        // Example logic: Ensure the player has achieved the required rank
        return dPlayer.getRankEnum().getPriority() >= rank.getPriority();
    }

    private void claimReward(Player player, DPlayer dPlayer, Ranks rank, Reward reward) {
        // Mark the reward as taken
        dPlayer.takeReward(rank);

        // Execute the reward commands
        for (String command : reward.getCommands()) {
            String formattedCommand = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
        }

        // Send a success message to the player
        player.sendMessage(ChatFormater.Color("&aYou have successfully claimed the " + rank.getName() + " reward!"));
        Sounds.SUCCESS.Play(player);
        core.getGuiManager().getRewardManager().openRewardInventory(player);
    }
}
