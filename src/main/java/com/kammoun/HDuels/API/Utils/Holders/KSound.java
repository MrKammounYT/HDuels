package com.kammoun.HDuels.API.Utils.Holders;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class KSound {

    private final Sound sound;
    private final float volume;
    private final float aFloat;

    public KSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.aFloat = pitch;
    }
    public KSound(Sound sound) {
        this.sound = sound;
        this.volume = 1.0f;
        this.aFloat = 1.0f;
    }

    public void Play(Player p){
        if(sound == null)return;
        p.playSound(p.getLocation(),sound,volume,aFloat);
    }
}
