package com.kammoun.HDuels.API.Utils.Chat;

import org.bukkit.ChatColor;

public  abstract class ChatFormater {
    private static final HexColorCodes HEX_COLOR_CODES = new HexColorCodes();

    public static HexColorCodes getHexColorCodes() {
        return HEX_COLOR_CODES;
    }

    public static String Color(String toColor){
        if(toColor == null || toColor.isEmpty())return toColor;
        return ChatColor.translateAlternateColorCodes('&', HEX_COLOR_CODES.FormatHex(toColor));
    }

    public static String unColor(String fancyName) {
        if (fancyName == null || fancyName.isEmpty()) {
            return fancyName;
        }
        return fancyName.replaceAll("&#[0-9a-fA-F]{6}", "");
    }
}
