package com.kammoun.HDuels.API.Utils.Dependencies;

import com.kammoun.HDuels.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

public abstract class PlaceHolderAPI extends PlaceholderExpansion {

    private final String Identifier;
    private final String PluginVersion;

    public PlaceHolderAPI(String identifier, String pluginVersion) {
        Identifier = identifier;
        PluginVersion = pluginVersion;
    }

    public static boolean PlaceHolderAPIExists(Main main){
        return main.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @Override
    public @NotNull String getIdentifier() {
        return Identifier;
    }

    @Override
    public @NotNull String getAuthor() {
        return "kammoun";
    }

    @Override
    public @NotNull String getVersion() {
        return PluginVersion;
    }


}
