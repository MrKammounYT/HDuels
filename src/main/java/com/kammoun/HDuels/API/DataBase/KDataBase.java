package com.kammoun.HDuels.API.DataBase;

import com.kammoun.HDuels.Managers.Core;

import java.sql.Connection;

public interface KDataBase {

    boolean connect(Core coreManager);
    Connection getConnection();

    com.kammoun.HDuels.Data.PlayerTable getPlayerTable();

}