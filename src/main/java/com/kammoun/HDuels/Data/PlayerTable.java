package com.kammoun.HDuels.Data;

import com.kammoun.HDuels.API.DataBase.KDataBase;
import com.kammoun.HDuels.API.DataBase.MTable;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import org.bukkit.entity.Player;

import java.sql.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerTable extends MTable {

    public PlayerTable(Core coreManager, KDataBase kDataBase) {
        super(coreManager, kDataBase);

    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS HDuels (" +
                "UUID VARCHAR(36) PRIMARY KEY," +
                "Name TEXT NOT NULL," +
                "POINTS INTEGER DEFAULT 0," +
                "KILLS INTEGER DEFAULT 0," +
                "GAMESPLAYED INTEGER DEFAULT 0," +
                "LOSSES INTEGER DEFAULT 0," +
                "DEATHS INTEGER DEFAULT 0," +
                "WINS INTEGER DEFAULT 0"
                +",REWARDS VARCHAR NOT NULL);";

        try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean PlayerExists(Player p) {
        String sql = "SELECT UUID FROM HDuels WHERE UUID = ?";
        try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayer(Player p) {
        if (PlayerExists(p)) return;
        String sql = "INSERT INTO HDuels (UUID, Name, POINTS, KILLS, GAMESPLAYED, LOSSES, DEATHS, WINS,REWARDS) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            preparedStatement.setString(2, p.getName());
            preparedStatement.setInt(3, 0); // Default points
            preparedStatement.setInt(4, 0); // Default kills
            preparedStatement.setInt(5, 0); // Default games played
            preparedStatement.setInt(6, 0); // Default losses
            preparedStatement.setInt(7, 0); // Default deaths
            preparedStatement.setInt(8, 0); // Default wins
            preparedStatement.setString(9,"0,0,0,0,0,0");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DPlayer getDPlayer(Player p) {
        createPlayer(p);
            String sql = "SELECT * FROM HDuels WHERE UUID = ?";
            try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
                preparedStatement.setString(1, p.getUniqueId().toString());
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String uuid = resultSet.getString("UUID");
                    int points = resultSet.getInt("POINTS");
                    int kills = resultSet.getInt("KILLS");
                    int gamesPlayed = resultSet.getInt("GAMESPLAYED");
                    int losses = resultSet.getInt("LOSSES");
                    int deaths = resultSet.getInt("DEATHS");
                    int wins = resultSet.getInt("WINS");
                    String RewardSequence = resultSet.getString("REWARDS");
                    return new DPlayer(UUID.fromString(uuid), p.getName(), points, kills, gamesPlayed, losses, deaths, wins,RewardSequence);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return null;
    }
    public void savePlayerData(DPlayer dPlayer) {
        String sql = "UPDATE HDuels SET POINTS =?, KILLS =?, GAMESPLAYED =?, LOSSES =?, DEATHS =?, WINS =? ,Name=?,REWARDS=? WHERE UUID =?";
        try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, dPlayer.getPoint());
            preparedStatement.setInt(2, dPlayer.getKills());
            preparedStatement.setInt(3, dPlayer.getGamesPlayed());
            preparedStatement.setInt(4, dPlayer.getLosses());
            preparedStatement.setInt(5, dPlayer.getDeaths());
            preparedStatement.setInt(6, dPlayer.getWins());
            preparedStatement.setString(7, dPlayer.getName());
            preparedStatement.setString(8,dPlayer.getRewardSequence());
            preparedStatement.setString(9, dPlayer.getUuid().toString());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public CompletableFuture<ArrayList<LeaderboardInfoHolder>> getLeaderboardsPlayersAsync(Core core,int page) {
        int pageSize = 45;
        int offset = (page - 1) * pageSize;
        String sql = "SELECT UUID, Name, POINTS FROM HDuels ORDER BY POINTS DESC LIMIT ? OFFSET ?";
        return CompletableFuture.supplyAsync(() -> {
            //update data
            for (DPlayer dPlayer : core.getPlayerManager().getOnlineDPlayersList()){
                String sql2 = "UPDATE HDuels SET POINTS =?,Name=? WHERE UUID =?";
                try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql2)) {
                    preparedStatement.setInt(1, dPlayer.getPoint());
                    preparedStatement.setString(2, dPlayer.getName());
                    preparedStatement.setString(3, dPlayer.getUuid().toString());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //update data
            ArrayList<LeaderboardInfoHolder> leaderboardPlayers = new ArrayList<>();
            try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
                preparedStatement.setInt(1, pageSize);
                preparedStatement.setInt(2, offset);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    if(resultSet.getString("UUID") == null)continue;
                    UUID uuid = UUID.fromString(resultSet.getString("UUID"));
                    String name = resultSet.getString("Name");
                    int points = resultSet.getInt("POINTS");
                    if(name != null && uuid != null){
                        LeaderboardInfoHolder player = new LeaderboardInfoHolder(uuid, name, points);
                        leaderboardPlayers.add(player);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return leaderboardPlayers;
        });
    }

    public void delete() {
        String sql = "DROP TABLE IF EXISTS HDuels";
        try (PreparedStatement preparedStatement = sqlManager.getConnection().prepareStatement(sql)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
