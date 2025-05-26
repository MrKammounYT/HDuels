package com.kammoun.HDuels.Data;

import java.util.UUID;

public class LeaderboardInfoHolder{

    private UUID uuid;
    private String PlayerName;
    private int Points;

    public LeaderboardInfoHolder(UUID uuid,String playerName, int points) {
        this.uuid = uuid;
        this.PlayerName = playerName;
        this.Points = points;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public int getPoints() {
        return Points;
    }

    public UUID getUuid() {
        return uuid;
    }
}
