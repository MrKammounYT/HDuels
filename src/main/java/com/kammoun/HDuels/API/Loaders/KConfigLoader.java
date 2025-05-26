package com.kammoun.HDuels.API.Loaders;

import com.kammoun.HDuels.Main;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public abstract class KConfigLoader<T> {

    private final ConcurrentHashMap<Integer,T> ItemsByID = new ConcurrentHashMap<Integer,T>();
    private final Main main;
    private int ItemsPerPage;

    public KConfigLoader(Main main) {
        this.main = main;
    }

    public File createConfigurationFile(String fileName){
        File file =new File(main.getDataFolder(), fileName);
        if (!file.exists()) {
            main.saveResource(fileName,false);
        }
        return file;
    }


    private int getMaxPages(){
        if(ItemsPerPage == 0)return 0;
        if(ItemsByID.size() % ItemsPerPage == 0)return ItemsByID.size()/ItemsPerPage;
        return (ItemsByID.size()/ItemsPerPage) +1;
    }


    public T getItem(int id) {
        if (!isValidItemID(id)) {
            throw new IllegalArgumentException("Invalid item ID: " + id);
        }
        return ItemsByID.get(id);
    }
    public boolean isValidItemID(int id){
        return ItemsByID.containsKey(id);
    }


}
