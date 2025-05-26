package com.kammoun.HDuels.Managers.Configurations;

import com.kammoun.HDuels.API.Loaders.KConfigLoader;
import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Chat.HexColorCodes;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Enums.Ranks;
import com.kammoun.HDuels.Utils.Others.PlaceHolderManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class RankRewardSystem extends KConfigLoader {

    private YamlConfiguration config;

    public RankRewardSystem(Main main) {
        super(main);
        File file = createConfigurationFile("RankRewards.yml");
        config = YamlConfiguration.loadConfiguration(file);

    }


   /* public void giveRankRewards(Player player, Ranks r) {
        String rank = r.getName().replaceAll("&#[0-9a-fA-F]{6}", "");
        if (config == null) {
            Bukkit.getLogger().warning("Config not loaded! Cannot give rewards.");
            return;
        }
        // Execute commands
        List<String> commands = config.getStringList("Ranks." + rank + ".Commands");
        if (!commands.isEmpty()) {
            for (String command : commands) {
                command = command.replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }

        // Send player messages
        List<String> playerMessages = config.getStringList("Ranks." + rank + ".Messages.Player");
        if (!playerMessages.isEmpty()) {
            for (String message : playerMessages) {
                message = message.replace("%player%", player.getName());
                player.sendMessage(PlaceHolderManager.ReplacePlaceHolders(player,ChatFormater.Color(message)));
            }
        }

        // Broadcast messages
        List<String> broadcastMessages = config.getStringList("Ranks." + rank + ".Messages.Broadcast");
        if (!broadcastMessages.isEmpty()) {
            for (String message : broadcastMessages) {
                message = message.replace("%player%", player.getName());
                Bukkit.broadcastMessage(PlaceHolderManager.ReplacePlaceHolders(player,ChatFormater.Color(message)));
            }
        }
        Bukkit.getLogger().info("Rewards and messages for rank " + rank + " given to " + player.getName());
    }*/
}