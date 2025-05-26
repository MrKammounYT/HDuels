package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.Utils.Enums.Ranks;

public class RankUtils {

    public static String getPlayerELO(int point) {
        return getPlayerRank(point).getName();
    }
    public static String getPlayerELOWithoutFont(int point) {
        return getPlayerRank(point).getFancyName();
    }
    public static Ranks getPlayerRank(int point) {
        if (point >= 2000) return Ranks.CHAMPION;
        else if (point >= 1250) return Ranks.DIAMOND;
        else if (point >= 1000) return Ranks.PLATINUM;
        else if (point >= 750) return Ranks.GOLD;
        else if (point >= 500) return Ranks.SILVER;
        else if (point >= 250) return Ranks.BRONZE;
        return Ranks.UNRANKED;
    }
    public static String getRankID(Ranks ranks) {
        return switch (ranks) {
            case BRONZE -> "Bronze";
            case SILVER -> "Silver";
            case GOLD -> "Gold";
            case PLATINUM -> "Platinum";
            case DIAMOND -> "Diamond";
            case CHAMPION -> "Champion";
            default -> "Unranked";
        };
    }
}
