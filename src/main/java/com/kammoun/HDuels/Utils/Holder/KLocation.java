package com.kammoun.HDuels.Utils.Holder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;


public class KLocation {

    private final String worldName;
    private final Vector vector;
    private final float yaw;
    private final float pitch;

    public KLocation(String worldName, Vector vector, float yaw, float pitch) {
        this.worldName = worldName;
        this.vector = vector;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public KLocation(Location location) {
        this.worldName = location.getWorld().getName();
        this.vector = location.toVector();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }
    public boolean isValid() {
        return worldName!= null && vector!= null && Bukkit.getWorld(worldName) != null;
    }
    public Location getLocation(){
        World world = Bukkit.getWorld(worldName);
        if(world == null) return null;
        Location location = vector.toLocation(world);
        location.setYaw(yaw);
        location.setPitch(pitch);
        return location;
    }

    public String getWorldName() {
        return worldName;
    }

    public Vector getVector() {
        return vector;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
