package com.kammoun.HDuels.Commands;

import com.kammoun.HDuels.API.Commands.KCommand;
import com.kammoun.HDuels.Main;
import com.kammoun.HDuels.Managers.Core;
import com.kammoun.HDuels.Utils.Holder.Arena;
import com.kammoun.HDuels.Utils.Holder.DPlayer;
import com.kammoun.HDuels.Utils.Others.LocationAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Duels extends KCommand implements TabCompleter {
    public Duels(Core core, String permissions) {
        super(core, permissions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!IsAPlayer(sender)) return true;
        if (!hasPermission(sender)) return true;

        Player player = getPlayer(sender);
        if (args.length == 0) {
            help(player);
            return true;
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "help":
                    help(player);
                    break;
                case "list":
                    ListArenas(player);
                    break;
                case "reload":
                    message(player, "&eReloading Plugin Config....");
                    core.reloadPlugin(Main.getInstance());
                    message(player, "&aPlugin Config has been reloaded!");
                    break;
                case "reset":
                    if (player.isOp()) {
                        message(player, "&eResetting Player Data....");
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                core.getMySQLManager().saveDataOnDisable();
                                core.getQueueManager().getPLAYERS_IN_QUEUE().clear();
                                core.getMySQLManager().getPlayerTable().delete();
                                core.getMySQLManager().getPlayerTable().createTable();
                                core.getPlayerManager().reCreatePlayers();
                            }
                        }.runTaskAsynchronously(Main.getInstance());
                        message(player, "&aPlayer Data has been reset!");
                    }
                    break;
                case "setspawn":
                    LocationAPI.setLocation(player.getLocation(), "spawn");
                    message(player, "&aSpawn has been set");
                    break;
                default:
                    message(player, "&cInvalid command. Use /hduel help for a list of commands.");
            }
            return true;
        }

        if (args.length == 2) {
            String cmd = args[0];
            String arenaName = args[1];

            switch (cmd.toLowerCase()) {
                case "create":
                    if (core.getArenaManager().ArenaExists(arenaName)) {
                        message(player, "&cThis Arena Already Exists!");
                    } else {
                        core.getArenaManager().createTempArena(arenaName);
                        message(player, "&aArena " + arenaName + " &ahas been created!");
                    }
                    return true;
                case "delete":
                    if (!core.getArenaManager().ArenaExists(arenaName)) {
                        message(player, "&cThis Arena doesn't Exist!");
                    } else {
                        core.getArenaManager().deleteArena(arenaName);
                        message(player, "&aArena " + arenaName + " &ahas been deleted!");
                    }
                    return true;
                case "save":
                    if (!core.getArenaManager().ArenaExists(arenaName)) {
                        message(player, "&cThis Arena doesn't Exist!");
                    } else {
                        Arena arena = core.getArenaManager().getArena(arenaName);
                        if (!arena.isValidToSave()) {
                            message(player, "&cThis Arena is missing some parameters!");
                        } else {
                            core.getArenaManager().saveArena(arena);
                            message(player, "&aArena has been saved!");
                        }
                    }
                    return true;
                case "setloc1":
                case "setloc2":
                    if (!core.getArenaManager().ArenaExists(arenaName)) {
                        message(player, "&cThis Arena doesn't Exist!");
                    } else {
                        Arena arena = core.getArenaManager().getArena(arenaName);
                        if (arena.getLocationWorld() != null && !player.getLocation().getWorld().getName().equals(arena.getLocationWorld())) {
                            message(player, "&cBoth Arena Locations must be in the same world!");
                        } else {
                            if (cmd.equalsIgnoreCase("setloc1")) {
                                arena.setLocation1(player.getLocation());
                                arena.setLocationWorld(player.getLocation().getWorld().getName());
                                message(player, "&aArena Location 1 has been set!");
                            } else if (cmd.equalsIgnoreCase("setloc2")) {
                                arena.setLocation2(player.getLocation());
                                arena.setLocationWorld(player.getLocation().getWorld().getName());
                                message(player, "&aArena Location 2 has been set!");
                            }
                        }
                    }
                    return true;
                default:
                    message(player, "&cInvalid command. Use /hduel help for a list of commands.");
                    return true;
            }
        }

        if (args.length == 3) {
            String cmd = args[0];
            String playerName = args[1];
            String amountStr = args[2];

            Player target = Bukkit.getPlayer(playerName);
            if (target == null) {
                message(player, "&cPlayer " + playerName + " is not online.");
                return true;
            }

            try {
                int amount = Integer.parseInt(amountStr);
                if (amount < 0) {
                    message(player, "&cAmount must be a positive number.");
                    return true;
                }

                DPlayer dPlayer = core.getPlayerManager().getPlayer(target);
                switch (cmd.toLowerCase()) {
                    case "givepoints":
                        dPlayer.addPoints(amount);
                        message(player, "&aSuccessfully gave " + amount + " points to " + playerName + ".");
                        message(target, "&aYou have received " + amount + " points from " + player.getName() + ".");
                        break;
                    case "getpoints":
                        int points = dPlayer.getPoints();
                        message(player, "&a" + playerName + " has " + points + " points.");
                        break;
                    case "setpoints":
                        dPlayer.setPoints(amount);
                        break;
                    case "setkills":
                        dPlayer.setKills(amount);
                        break;
                    case "setdeaths":
                        dPlayer.setDeaths(amount);
                        break;
                    case "setwins":
                        dPlayer.setWins(amount);
                        break;
                    case "setlosses":
                        dPlayer.setLosses(amount);
                        break;
                    case "setgamesplayed":
                        dPlayer.setGamesPlayed(amount);
                        break;
                    default:
                        message(player, "&cInvalid stat. Use /hduel help.");
                        return true;
                }
                message(player, "&aSuccessfully updated " + cmd.substring(3) + " for " + playerName);
            } catch (NumberFormatException e) {
                message(player, "&cInvalid number format.");
            }
            return true;
        }

        message(player, "&cInvalid number of arguments. Use /hduel help for a list of commands.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.addAll(Arrays.asList("help", "list", "reload", "reset", "setspawn", "create", "delete", "save", "setloc1", "setloc2", "givepoints", "getpoints", "setpoints", "setkills", "setdeaths", "setwins", "setlosses", "setgamesplayed"));
        } else if (args.length == 2) {
            if (Arrays.asList("create", "delete", "save", "setloc1", "setloc2").contains(args[0].toLowerCase())) {
                completions.addAll(core.getArenaManager().getArenasNames());
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    completions.add(p.getName());
                }
            }
        } else if (args.length == 3 && Arrays.asList("givepoints", "getpoints", "setpoints", "setkills", "setdeaths", "setwins", "setlosses", "setgamesplayed").contains(args[0].toLowerCase())) {
            completions.add("1");
            completions.add("10");
            completions.add("100");
        }
        return completions;
    }

    private void ListArenas(Player player) {
        List<String> arenas = core.getArenaManager().getArenasNames();
        if (arenas.isEmpty()) {
            message(player, "&cThere are no arenas available.");
        } else {
            message(player, "&6&lAvailable Arenas:");
            for (String arenaName : arenas) {
                message(player, "&e- " + arenaName);
            }
        }
    }

    private void help(Player player) {
        message(player, "&6&lDuels Commands:");
        message(player, "&e/hduel list &7- List all arenas.");
        message(player, "&e/hduel reload &7- Reload the duels configuration.");
        message(player, "&e/hduel create <arenaName> &7- Create a new arena.");
        message(player, "&e/hduel delete <arenaName> &7- Delete an existing arena.");
        message(player, "&e/hduel setloc1 <arenaName> &7- Set the first location of the arena.");
        message(player, "&e/hduel setloc2 <arenaName> &7- Set the second location of the arena.");
        message(player, "&e/hduel save <arenaName> &7- Save the arena configuration.");
        message(player, "&e/hduel givepoints <player> <amount> &7- Give points to a player.");
        message(player, "&e/hduel getpoints <player> &7- Get the points of a player.");
        message(player, "&e/hduel setpoints <player> <amount> &7- Set a player's points.");
        message(player, "&e/hduel setkills <player> <amount> &7- Set a player's kills.");
        message(player, "&e/hduel setdeaths <player> <amount> &7- Set a player's deaths.");
        message(player, "&e/hduel setwins <player> <amount> &7- Set a player's wins.");
        message(player, "&e/hduel setlosses <player> <amount> &7- Set a player's losses.");
        message(player, "&e/hduel setgamesplayed <player> <amount> &7- Set a player's games played.");
        message(player, "&e/hduel reset &7- &cReset the duels data!.");
    }
}