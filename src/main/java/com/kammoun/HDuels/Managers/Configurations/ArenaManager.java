package com.kammoun.HDuels.Managers.Configurations;

import com.kammoun.HDuels.API.Loaders.KConfigLoader;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Holder.Arena;
import com.kammoun.HDuels.Utils.Holder.KLocation;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ArenaManager extends KConfigLoader {

    private final YamlConfiguration config;
    private final File file;
    private final ConcurrentHashMap<String, Arena> arenaMap = new ConcurrentHashMap<>();

    public ArenaManager(Main main) {
        super(main);
        file = createConfigurationFile("Arena/Arenas.yml");
        config = YamlConfiguration.loadConfiguration(file);
        loadArenas();
    }

    public void createTempArena(String name) {
        if (ArenaExists(name)) return;
        arenaMap.put(name, new Arena(name));
    }

    public Arena getAvailableArena() {
        List<Arena> arenas = new ArrayList<>(arenaMap.values());
        Collections.shuffle(arenas);
        for (Arena arena : arenas) {
            if (arena.isValidToPlay()) {
                return arena;
            }
        }
        return null;
    }

    public void loadArenas() {
        if (config.getConfigurationSection("Arenas") == null) return;

        for (String arenaName : config.getConfigurationSection("Arenas").getKeys(false)) {
            String world = config.getString("Arenas." + arenaName + ".World");
            KLocation loc1 = stringToKLocation(config.getString("Arenas." + arenaName + ".Loc1"));
            KLocation loc2 = stringToKLocation(config.getString("Arenas." + arenaName + ".Loc2"));
            if (world != null && loc1 != null && loc2 != null) {
                arenaMap.put(arenaName, new Arena(arenaName, world, loc1, loc2));
            }
        }
        Main.Inform("&aLoaded &e" + arenaMap.size() + " &aArenas from the config");
    }

    public Arena getArena(String name) {
        return arenaMap.get(name);
    }

    public boolean ArenaExists(String name) {
        return arenaMap.containsKey(name);
    }

    public void deleteArena(String name) {
        if (arenaMap.containsKey(name)) {
            config.set("Arenas." + name, null);
            saveConfig();
            arenaMap.remove(name);
        }
    }

    public void saveArena(Arena arena) {
        String path = "Arenas." + arena.getName();
        config.set(path + ".Loc1", kLocationToString(arena.getKlocation1()));
        config.set(path + ".Loc2", kLocationToString(arena.getKlocation2()));
        config.set(path + ".World", arena.getLocationWorld());
        saveConfig();
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private @NotNull String kLocationToString(KLocation location) {
        return location.getWorldName() + ","
                + location.getVector().getX() + ","
                + location.getVector().getY() + ","
                + location.getVector().getZ() + ","
                + location.getYaw() + ","
                + location.getPitch();
    }

    private KLocation stringToKLocation(String str) {
        if (str == null || !str.contains(",")) return null;
        String[] parts = str.split(",");
        if (parts.length < 6) return null;
        try {
            String world = parts[0];
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);

            return new KLocation(world, new Vector(x, y, z), yaw, pitch);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getArenasNames() {
        return arenaMap.keySet().stream().toList();
    }
}


