package com.kammoun.HDuels.Managers.Data;

import com.kammoun.HDuels.API.DataBase.KDataBase;
import com.kammoun.HDuels.Data.PlayerTable;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.kammoun.HDuels.Main.Inform;

public class MySQLManager implements KDataBase {
    private Connection connection;
    private PlayerTable playerTable;
    private Core core;

    public MySQLManager(Main Main, Core coreManager) {
        this.core = coreManager;
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the SQLite database (file-based)
            String dbPath = Main.getDataFolder().getAbsolutePath() + "/hduels.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            if (isConnected()) {
                Inform("&aSuccessfully Connected to the SQLite Database!");
            } else {
                Inform("&cCouldn't Connect to the SQLite Database!");
                Main.onDisable();
                return;
            }

            // Initialize the player table
            playerTable = new PlayerTable(coreManager, this);
        } catch (Exception e) {
            e.printStackTrace();
            Inform("&cCouldn't Connect to the SQLite Database!");
        }
    }

    public PlayerTable getPlayerTable() {
        return playerTable;
    }

    @Override
    public boolean connect(Core coreManager) {
        // SQLite doesn't require explicit connection handling like MySQL
        return isConnected();
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void saveDataOnDisable() {
        try {
            for (DPlayer onlinePlayer : core.getPlayerManager().getOnlineDPlayersList()) {
                playerTable.savePlayerData(onlinePlayer);
                core.getPlayerManager().removePlayer(onlinePlayer.getUuid());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePlayerData(Player player) {
        DPlayer dPlayer = core.getPlayerManager().getPlayer(player);
        if (dPlayer == null) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                playerTable.savePlayerData(dPlayer);
                core.getPlayerManager().removePlayer(dPlayer.getUuid());
            }
        }.runTaskAsynchronously(Main.getInstance());
    }
}