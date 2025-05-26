package com.kammoun.HDuels.Utils.Enums;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;

public enum Ranks {
    UNRANKED(0,"&#bebebeUnranked","&#bebebeᴜɴʀᴀɴᴋᴇᴅ"),
    BRONZE(1,"&#d19f1aBronze","&#d19f1aʙʀᴏɴᴢᴇ")
    ,SILVER(2,"&#a0a8a8Silver","&#a0a8a8ꜱɪʟᴠᴇʀ")
    ,GOLD(3,"&#efbf04Gold","&#efbf04ɢᴏʟᴅ")
    ,PLATINUM(4,"&#1aa5b5Platinum","&#1aa5b5ᴘʟᴀᴛɪɴᴜᴍ")
    ,DIAMOND(5,"&#2ecfe1Diamond","&#2ecfe1ᴅɪᴀᴍᴏɴᴅ")
    ,CHAMPION(6,"&#f01f1fChampion","&#f01f1fᴄʜᴀᴍᴘɪᴏɴ");

    private int priority;
    private String name;
    private String fancyName;
    Ranks(int priority,String name, String fancyName){
        this.priority = priority;
        this.name = name;
        this.fancyName = fancyName;
    }

    public int getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    public String getFancyName() {
        return fancyName;
    }
    public String getNonColoredFancyName() {
        return ChatFormater.unColor(fancyName);
    }
}
