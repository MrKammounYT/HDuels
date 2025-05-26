package com.kammoun.HDuels;

import com.kammoun.HDuels.API.Utils.Dependencies.PlaceHolderAPI;
import com.kammoun.HDuels.Commands.Duels;
import com.kammoun.HDuels.Commands.Player.Collect;
import com.kammoun.HDuels.Commands.Player.Duel;
import com.kammoun.HDuels.Commands.Player.Leave;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {


    private Core core;
    private String prefix = ""; // Instance-specific prefix
    private static Main instance; // Instance of the current plugin
    private  MessageManager messageManager = new MessageManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        messageManager.loadMessages(this);
        WaterMark();
        if (!NBT.preloadApi()) {
            getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            getPluginLoader().disablePlugin(this);
            return;
        }
        if(PlaceHolderAPI.PlaceHolderAPIExists(this)){
            new com.kammoun.HDuels.Utils.Others.PlaceHolderAPI().register();
        }
        if (!Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI")){
            getLogger().severe("*** NoteBlockAPI is not installed or not enabled. ***");
            onDisable();
            return;
        }
        core = new Core(this);
        prefix = getConfig().getString("Prefix","");
        getCommand("hduel").setExecutor(new Duels(core,"duels.admin"));
        getCommand("duel").setExecutor(new Duel(core,""));
        getCommand("leave").setExecutor(new Leave(core,""));
        getCommand("collect").setExecutor(new Collect(core,""));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(core == null) return;
        core.getMySQLManager().saveDataOnDisable();
    }
    public static void Inform(String message) {
        if (message == null || instance == null) return;
        String formattedMessage = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.getConsoleSender().sendMessage(instance.prefix + formattedMessage);
    }
    public static Main getInstance() {
        return instance;
    }

    public Core getCore() {
        return core;
    }
    public void reloadMain(){
        this.messageManager = new MessageManager();
    }

    public void WaterMark() {
        Inform("&e&m----------------------------------------------------------------");
        Inform("&cThis Plugin was Created by Kammoun !");
    }
}
