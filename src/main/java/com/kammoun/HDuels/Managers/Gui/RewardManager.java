package com.kammoun.HDuels.Managers.Gui;


import com.kammoun.HDuels.API.Loaders.KConfigLoader;
import com.kammoun.HDuels.API.Loaders.KItemLoader;
import com.kammoun.HDuels.API.Loaders.KMenuLoader;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Enums.Ranks;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Holder.Inventory.RewardInventoryHolder;
import com.kammoun.HDuels.Utils.Holder.Reward;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardManager extends KMenuLoader {
    private final Core core;
    private final Map<Ranks, Reward> rewardsMap = new HashMap<Ranks, Reward>();

    public RewardManager(Main main,Core core) {
        super(main,"Gui/Reward.yml");
        this.core = core;
        File file = createConfigurationFile("Gui/Reward.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        this.InventoryName = config.getString("Inventory.name");
        this.InventorySize = config.getInt("Inventory.size");
        load(config);
    }

    public void load(FileConfiguration config) {
        for (Ranks rank : Ranks.values()) {
            // Skip UNRANKED since it doesn't have rewards
            if (rank == Ranks.UNRANKED) continue;

            // Get the configuration section for the rank
            ConfigurationSection rankSection = config.getConfigurationSection(rank.name());
            if (rankSection == null) {
                System.out.println("No configuration found for rank: " + rank.name());
                continue;
            }

            // Load ClaimItem, AlreadyClaimed, and CantClaimIt
            KammounItem claimItem = KItemLoader.getOptionItemFromConfig(rankSection.getConfigurationSection("ClaimItem"));
            KammounItem alreadyClaimed = KItemLoader.getOptionItemFromConfig(rankSection.getConfigurationSection("AlreadyClaimed"));
            KammounItem cantClaimIt = KItemLoader.getOptionItemFromConfig(rankSection.getConfigurationSection("CantClaimIt"));

            // Load commands
            List<String> commands = rankSection.getStringList("Commands");

            // Create a Reward object and add it to the map
            Reward reward = new Reward(claimItem, alreadyClaimed, cantClaimIt, new ArrayList<>(commands));
            rewardsMap.put(rank, reward);
        }
    }

    public Reward getReward(Ranks rank) {
        return rewardsMap.get(rank);
    }
    public Map<Ranks, Reward> getRewardsMap() {
        return rewardsMap;
    }
    public void openRewardInventory(Player p){
        DPlayer dPlayer =core.getPlayerManager().getPlayer(p);
        if(dPlayer == null)return;
        Inventory inv = Bukkit.createInventory(new RewardInventoryHolder(),InventorySize, ChatFormater.Color(InventoryName));
        for(Map.Entry<Ranks, Reward> rewards : rewardsMap.entrySet()){
            KammounItem item = rewards.getValue().getAppropriateRewardItem(dPlayer,rewards.getKey());
            inv.setItem(item.getSlot(),item.getItem());
        }
        p.openInventory(inv);
    }
}