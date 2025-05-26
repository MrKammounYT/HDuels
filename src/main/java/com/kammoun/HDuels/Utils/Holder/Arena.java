package com.kammoun.HDuels.Utils.Holder;

import com.kammoun.HDuels.Utils.Enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Location;


public class Arena {


    private GameState ArenaState;
    private final String ArenaName;
    private KLocation location1;
    private KLocation location2;
    private String LocationWorld;
    public Arena(String arenaName, String locationWorld,KLocation location1, KLocation location2) {
        this.ArenaName = arenaName;
        this.location1 = location1;
        this.location2 = location2;
        this.LocationWorld = locationWorld;
        this.ArenaState = GameState.WAITING;
    }

    public Arena(String arenaName) {
        ArenaName = arenaName;
        this.ArenaState = GameState.SETUP;
    }

    public GameState getArenaState() {
        return ArenaState;
    }

    public String getLocationWorld() {
        return LocationWorld;
    }

    public String getName() {
        return ArenaName;
    }

    public KLocation getKlocation1() {
        return location1;
    }

    public KLocation getKlocation2() {
        return location2;
    }

    public void setLocation1(Location location) {
        this.location1  = new KLocation(location);
    }

    public void setLocation2(Location location2) {
        this.location2  = new KLocation(location2);
    }

    public void setLocationWorld(String locationWorld) {
        LocationWorld = locationWorld;
    }

    public boolean isValidToSave(){
        return LocationWorld!= null && Bukkit.getWorld(LocationWorld) != null && location1 != null && location2 != null;
    }
    public boolean isValidToPlay(){
        return isValidToSave() && ArenaState == GameState.WAITING;
    }

    public Location getSpawn1() {
        return location1.getLocation();
    }

    public Location getSpawn2() {
        return location2.getLocation();
    }

    public void setArenaState(GameState arenaState) {
        ArenaState = arenaState;
    }

    public void reset(){
        ArenaState = GameState.WAITING;
    }
}
