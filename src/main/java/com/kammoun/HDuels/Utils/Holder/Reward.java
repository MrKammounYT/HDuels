package com.kammoun.HDuels.Utils.Holder;

import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import com.kammoun.HDuels.Utils.Enums.Ranks;

import java.util.ArrayList;

public class Reward {
    private final KammounItem claimItem;
    private final KammounItem alreadyClaimed;
    private final KammounItem cantClaimIt;
    private final ArrayList<String> commands;

    public Reward(KammounItem claimItem, KammounItem alreadyClaimed, KammounItem cantClaimIt, ArrayList<String> commands) {
        this.claimItem = claimItem;
        this.alreadyClaimed = alreadyClaimed;
        this.cantClaimIt = cantClaimIt;
        this.commands = commands;
    }

    public KammounItem getClaimItem() {
        return claimItem;
    }
    public KammounItem getAlreadyClaimed() {
        return alreadyClaimed;
    }

    public KammounItem getCantClaimIt() {
        return cantClaimIt;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public KammounItem getAppropriateRewardItem(DPlayer dPlayer, Ranks RewardRank){
        if(dPlayer.hasTakenReward(RewardRank)){
            return alreadyClaimed;
        }
        else if(dPlayer.getRankEnum().getPriority() < RewardRank.getPriority())return cantClaimIt;
        return claimItem;
    }
}