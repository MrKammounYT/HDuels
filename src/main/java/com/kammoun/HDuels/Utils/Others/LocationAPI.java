package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;

public class LocationAPI {
    private static final File file = new File(Main.getInstance().getDataFolder(),"Locations.yml");
    private static final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


    public static Location getLocation(String locationName){
        if(config.getConfigurationSection(locationName) == null) {
            Main.Inform(locationName + " &cIs not set !");
            return null;
        }
        String world = config.getString(locationName + ".world");
        double x = config.getDouble(locationName + ".x");
        double y = config.getDouble(locationName + ".y");
        double z = config.getDouble(locationName + ".z");
        double yaw = config.getDouble(locationName + ".yaw");
        double pitch = config.getDouble(locationName + ".pitch");
        if(Bukkit.getWorld(world) ==null){
            Main.Inform(world+" &cDoesn't exists !");
            return null;
        }
        Location loc = new Location(Bukkit.getWorld(world), x, y, z);
        loc.setYaw((float)yaw);
        loc.setPitch((float)pitch);
        return loc;

    }
    public static String getWorldNameForLocation(String locationName){
        String world = config.getString(locationName + ".world");
        if(world == null)return null;
        return world;
    }

    public static void setLocation(Location loc, String name) {
        String world = loc.getWorld().getName();
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        double yaw = loc.getYaw();
        double pitch = loc.getPitch();
        config.set(name + ".world", (Object)world);
        config.set(name + ".x", (Object)x);
        config.set(name + ".y", (Object)y);
        config.set(name + ".z", (Object)z);
        config.set(name + ".yaw", (Object)yaw);
        config.set(name + ".pitch", (Object)pitch);
        try {
            config.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Vector getLocationVector(String name){
        Location loc = getLocation(name);
        if(loc == null){
            throw new IllegalStateException("The location "+name + "Doesn't exists ");
        }
        return loc.toVector();
    }


    public static String getWorldName(String locationName) {
        if(config.getConfigurationSection(locationName) == null) {
            Main.Inform(locationName + " &cIs not set !");
            return null;
        }
        String world = config.getString(locationName + ".world");
        if(Bukkit.getWorld(world) ==null){
            Main.Inform(world+" &cDoesn't exists !");
            return "null";
        }
        return world;
    }
}
