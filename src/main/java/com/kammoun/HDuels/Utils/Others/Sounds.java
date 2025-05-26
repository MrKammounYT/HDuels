package com.kammoun.HDuels.Utils.Others;

import com.kammoun.HDuels.API.Utils.Holders.KSound;
import org.bukkit.Sound;

public class Sounds {
    public static final KSound LOOT_INV_OPEN = new KSound(Sound.BLOCK_CHEST_OPEN,0.5f,0.4f);
    public static final KSound REFUSE = new KSound(Sound.ENTITY_VILLAGER_NO,0.5f,1.2f);
    public static final KSound INV_CLICK = new KSound(Sound.UI_BUTTON_CLICK,0.5f,0.8f);

    public static final KSound SUCCESS = new KSound(Sound.ENTITY_VILLAGER_YES,0.5f,1.2f);
}
