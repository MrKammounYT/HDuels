package com.kammoun.HDuels.Utils.Holder;

import com.kammoun.HDuels.Events.PlayerPointsUpdateEvent;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Enums.Ranks;
import com.kammoun.HDuels.Utils.Others.RankUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DPlayer {
    private UUID uuid;
    private String name;
    private int points;
    private int kills;
    private int gamesPlayed;
    private int losses;
    private int deaths;
    private int wins; // New field
    private HDuel PlayerCurrentDuel; // New field
    private String RewardSequence;


    public DPlayer(UUID uuid, String name, int points, int kills, int gamesPlayed, int losses, int deaths, int wins,String RewardSequence) {
        this.uuid = uuid;
        this.name = name;
        this.points = points;
        this.kills = kills;
        this.gamesPlayed = gamesPlayed;
        this.losses = losses;
        this.deaths = deaths;
        this.wins = wins; // Initialize wins
        this.RewardSequence = RewardSequence;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPoint() {
        return points;
    }
    public Ranks getRankEnum(){
        return RankUtils.getPlayerRank(points);
    }
    public String getRank(){
        return RankUtils.getPlayerELO(points);
    }
    public boolean isInDuel(){
        return PlayerCurrentDuel != null;
    }

    public HDuel getPlayerCurrentDuel() {
        return PlayerCurrentDuel;
    }

    public void setPlayerCurrentDuel(HDuel playerCurrentDuel) {
        PlayerCurrentDuel = playerCurrentDuel;
        if(playerCurrentDuel != null){
            gamesPlayed++;
        }
    }
    public int getWinRate() {
        if (wins+losses == 0) return wins;
        return (int) (((float) wins / (wins+losses)) * 100);
    }

    public float getKD(){
        if(deaths == 0) return kills;
        return ((float) kills / deaths);
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }
    public void addKills(int kills) {
        this.kills += kills;
    }
    public void addDeaths(int deaths) {
        this.deaths += deaths;
    }
    public void addWin() {
        wins++;
    }
    public void addLosses() {
        losses++;
    }
    public void addGamesPlayed() {
        gamesPlayed++;
    }
    public void addPoints(int point) {
        int oldPoints =this.points;
        this.points += point;
        Bukkit.getPluginManager().callEvent(new PlayerPointsUpdateEvent(this,oldPoints,this.points));
    }

    public void removePoints(int points) {
        if(this.points <= 0)return;
        this.points -= points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        int oldPoints =this.points;
        this.points = points;
        Bukkit.getPluginManager().callEvent(new PlayerPointsUpdateEvent(this,oldPoints,points));
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public boolean hasTakenReward(Ranks rank) {
        if (RewardSequence == null || RewardSequence.isEmpty()) {
            RewardSequence = "0,0,0,0,0,0,0";
        }
        String[] ranks = RewardSequence.split(",");
        int rankID = rank.getPriority();

        // Check if the rankID is within the bounds of the array
        if (rankID >= ranks.length) {
            return false;
        }

        return ranks[rankID].equals("1");
    }

    // Mark a reward as taken for a specific rank
    public void takeReward(Ranks rank) {
        if (RewardSequence == null || RewardSequence.isEmpty()) {
            RewardSequence = "0,0,0,0,0,0,0";
        }
        String[] ranks = RewardSequence.split(",");
        int rankID = rank.getPriority();

        // If the rankID is out of bounds, expand the array
        if (rankID >= ranks.length) {
            String[] newRanks = new String[rankID + 1];
            System.arraycopy(ranks, 0, newRanks, 0, ranks.length);

            // Initialize new positions with "0"
            for (int i = ranks.length; i < newRanks.length; i++) {
                newRanks[i] = "0";
            }

            ranks = newRanks;
        }

        // Mark the reward as taken
        ranks[rankID] = "1";
        RewardSequence = String.join(",", ranks);
    }

    public String getRewardSequence() {
        return RewardSequence;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
