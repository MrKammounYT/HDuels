package com.kammoun.HDuels.Utils.Holder;

import com.kammoun.HDuels.API.Utils.Chat.ChatFormater;
import com.kammoun.HDuels.API.Utils.Holders.KammounItem;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Utils.Enums.GameState;
import com.kammoun.HDuels.Utils.Holder.Inventory.DuelLootHolder;
import com.kammoun.HDuels.Utils.Others.LocationAPI;
import com.kammoun.HDuels.Utils.Others.MessageManager;
import com.kammoun.HDuels.Utils.Others.Sounds;
import com.kammoun.HDuels.Utils.Others.SurvivalGuide;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class HDuel {
    private final Arena DuelArena;
    private final DPlayer dPlayer1;
    private final DPlayer dPlayer2;
    private final CopyOnWriteArrayList<UUID> DuelsPlayers = new CopyOnWriteArrayList<UUID>();
    private List<ItemStack> ItemsInventory = new CopyOnWriteArrayList<>();

    public HDuel(Arena duelArena, DPlayer dPlayer1, DPlayer dPlayer2) {
        DuelArena = duelArena;
        this.dPlayer1 = dPlayer1;
        this.dPlayer2 = dPlayer2;
        DuelsPlayers.add(dPlayer1.getUuid());
        DuelsPlayers.add(dPlayer2.getUuid());
    }

    public void sendMessage(String message) {
        String coloredMessage = ChatFormater.Color(message);
        org.bukkit.entity.Player player1Entity = Bukkit.getPlayer(dPlayer1.getUuid());
        if (player1Entity != null && player1Entity.isOnline()) {
            player1Entity.sendMessage(coloredMessage);
        }
        org.bukkit.entity.Player player2Entity = Bukkit.getPlayer(dPlayer2.getUuid());
        if (player2Entity != null && player2Entity.isOnline()) {
            player2Entity.sendMessage(coloredMessage);
        }
    }
    public Arena getDuelArena() {
        return DuelArena;
    }

    public void eliminatePlayer(Player p,DPlayer dPlayer) {
        DuelsPlayers.remove(p.getUniqueId());
        dPlayer.setPlayerCurrentDuel(null);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(LocationAPI.getLocation("spawn") == null)return;
                p.teleport(Objects.requireNonNull(LocationAPI.getLocation("spawn")));
            }
        }.runTaskLater(Main.getInstance(),10L);
        if(DuelArena.getArenaState().equals(GameState.ITEMCOLLECT))return;
        dPlayer.addLosses();
        dPlayer.addDeaths(1);
        int PointLost =new Random().nextInt(17,20);
        dPlayer.removePoints(PointLost);
        MessageManager.sendPointLost(p,PointLost);
        //inv
        ItemStack[] mainInventory = p.getInventory().getContents();
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for(ItemStack i : mainInventory){
            if(i== null)continue;
            if(SurvivalGuide.isSurvivalGuide(i))continue;
            items.add(i);
        }
        ItemsInventory.addAll(items);
        p.getInventory().clear();
        //inv
    }
    public void openLootCollectInventory(Player p){
        Inventory inv = Bukkit.createInventory(new DuelLootHolder(),54, ChatFormater.Color("       ᴄᴏʟʟᴇᴄᴛ ʏᴏᴜʀ ʟᴏᴏᴛ"));
        if(getItemsInventory() != null){
            for (ItemStack items : getItemsInventory()){
                if(items!= null) inv.addItem(items);
            }
        }
        p.openInventory(inv);
        Sounds.LOOT_INV_OPEN.Play(p);
    }

    public CopyOnWriteArrayList<UUID> getDuelsPlayers() {
        return DuelsPlayers;
    }

    public List<ItemStack> getItemsInventory() {
        return ItemsInventory;
    }

    public void reset(){
        DuelsPlayers.clear();
        DuelArena.reset();
        dPlayer1.setPlayerCurrentDuel(null);
        dPlayer2.setPlayerCurrentDuel(null);
        getDuelArena().setArenaState(GameState.WAITING);
    }

    public DPlayer getDPlayer1() {
        return dPlayer1;
    }

    public DPlayer getDPlayer2() {
        return dPlayer2;
    }

    public void setItemsInventory(List<ItemStack> lootInventory) {
        this.ItemsInventory = lootInventory;
    }
    public Player getPlayer1() {
        return Bukkit.getPlayer(getDPlayer1().getUuid());
    }

    public Player getPlayer2() {
        return Bukkit.getPlayer(getDPlayer2().getUuid());
    }
    public String getLoserName(Player winner) {
        if (getDPlayer1().getUuid().equals(winner.getUniqueId())) {
            return getDPlayer2().getName();
        }
        return getDPlayer1().getName();
    }


}
