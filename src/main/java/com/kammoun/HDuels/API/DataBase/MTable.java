package com.kammoun.HDuels.API.DataBase;

import com.kammoun.HDuels.API.DataBase.KDataBase;
import com.kammoun.HDuels.Managers.Core;

public abstract class MTable {
    public Core core;
    public KDataBase sqlManager;

    public MTable(Core coreManager, KDataBase kDataBase) {
        this.core = coreManager;
        this.sqlManager = kDataBase;
        createTable();
    }
    public abstract void createTable();
}
