package com.kammoun.HDuels.Managers;

import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SongsManager {

    private final JavaPlugin plugin;
    private final File songsFolder;
    private HashSet<Song> songs;
    private final ConcurrentHashMap<UUID, RadioSongPlayer> playerSongPlayerMap = new ConcurrentHashMap<>();


    public SongsManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.songsFolder = new File(plugin.getDataFolder(), "songs");
        loadSongs();

    }
    public HashSet<Song> getSongs() {
        return songs;
    }
    public void playSong(Player p){
        if (songs.isEmpty()) {
            p.sendMessage("§cNo songs found in the songs folder :(");
        } else {
            List<Song> shuffledSongs = new ArrayList<>(songs);
            Collections.shuffle(shuffledSongs);
            Playlist playlist = new Playlist(shuffledSongs.toArray(new Song[0]));
            RadioSongPlayer rsp = new RadioSongPlayer(playlist);
            rsp.setVolume((byte) 20);
            rsp.addPlayer(p);
            if(shuffledSongs.size() <= 1){
                rsp.setRepeatMode(RepeatMode.ALL);
            }
            rsp.setPlaying(true);
            playerSongPlayerMap.put(p.getUniqueId(), rsp);
        }
    }
    public void stopSong(Player p){
        if (playerSongPlayerMap.containsKey(p.getUniqueId())) {
            playerSongPlayerMap.get(p.getUniqueId()).destroy();
            playerSongPlayerMap.remove(p.getUniqueId());
        }
    }
    public void stopSongsForAllPlayers(){
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            stopSong(p);
        }
    }
    private void loadSongs() {
        songs = new HashSet<>();
        if (!songsFolder.exists()) {
            songsFolder.mkdirs(); // Create the songs folder if it doesn't exist
            plugin.getLogger().info("Created songs folder.");
            return;
        }

        File[] files = songsFolder.listFiles((dir, name) -> name.endsWith(".nbs"));
        if (files == null || files.length == 0) {
            plugin.getLogger().warning("No songs found in the songs folder.");
            return;
        }

        for (File file : files) {
            try {
                Song song = NBSDecoder.parse(file);
                if (song != null) {
                    songs.add(song);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load song: " + file.getName());
                e.printStackTrace();
            }
        }
        plugin.getLogger().info("Loaded "+songs.size()+" songs!");
    }
}