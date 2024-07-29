package com.ranper.commands;

import com.ranper.listeners.WindChargeListener;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WindChargeCommands implements CommandExecutor {

    private final WindChargeListener listener;

    public WindChargeCommands (WindChargeListener listener) {
        this.listener = listener;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if(args[0].equalsIgnoreCase("velocity") || args[0].equalsIgnoreCase("speed")) {
                    try {
                        if(args.length < 2) {
                            String velocity = listener.getNewVelocity().toString();
                            player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.AQUA + "speed is: " + ChatColor.WHITE + velocity);
                        }
                        Double newVelocity = Double.parseDouble(args[1]);
                        player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.AQUA + "speed: " + ChatColor.WHITE +  args[1] + "," + ChatColor.GREEN + " has been set");
                        this.listener.setNewVelocity(newVelocity);
                        return false;
                    } catch (Exception e) {
                        if(args.length < 2) {
                            return false;
                        }
                        player.sendMessage(ChatColor.RED + "* Please insert a number");
                    }
                }
                if(args[0].equalsIgnoreCase("power")) {
                    try {
                        if(args.length < 2) {
                            String power = listener.getNewExplosionPower().toString();
                            player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.RED + "power is: " + ChatColor.WHITE + power);
                        }
                        float newVelocity = Float.parseFloat(args[1]);
                        player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.RED + "power: " + ChatColor.WHITE +  args[1] + "," + ChatColor.GREEN + " has been set");
                        this.listener.setNewExplosionPower(newVelocity);
                        return false;
                    } catch (Exception e) {
                        if(args.length < 2) {
                            return false;
                        }
                        player.sendMessage(ChatColor.RED + "* Please insert a number");
                    }
                }
                if(args[0].equalsIgnoreCase("particles")) {
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles list");
                        player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles <particle>");
                        player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles off");
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("list")) {
                        listParticles(player);
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("off")) {
                        if (hasParticle(player)) {
                            removeParticle(player);
                            player.sendMessage(ChatColor.AQUA + "Particles off");
                        } else {
                            player.sendMessage(ChatColor.RED + "You windcharge particles are off");
                        }
                        return false;
                    }
                    try {
                        Particle e = Particle.valueOf(args[1].toUpperCase());
                        addParticle(player, e);
                        player.sendMessage(ChatColor.GREEN + "Particle " + e.name().toLowerCase() + " has been set");
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Invalid Particle: " + args[1].toLowerCase());
                        player.sendMessage(ChatColor.RED + "Use: /home particle list");
                    }
                }
                if(args[0].equalsIgnoreCase("velocity/speed")) {
                    player.sendMessage(ChatColor.GOLD + "Please use: /windcharge velocity or /windcharge speed");
                }
                if(args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge velocity/speed <speed>" , ChatColor.AQUA +"(To change speed of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge power <strength>", ChatColor.AQUA + "(To change explosion strength of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles <particle>", ChatColor.AQUA + "(To change particles of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge velocity/speed", ChatColor.AQUA + "(To get speed of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge power", ChatColor.AQUA + "(To get explosion strength of windcharge)");
                }
            } else {
                player.sendMessage(ChatColor.GOLD + "Use: /windcharge help");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "/windcharge is a player command only!");
        }
        return false;
    }

    private void listParticles (Player p) {
        String text = "";

        for (Particle pa : Particle.values()) {
            if (text == "") {
                text = pa.name();
            } else {
                text += ", " + pa.name();
            }
        }
        text += ", OFF";

        p.sendMessage(ChatColor.BLUE + text);
    }
    private boolean hasParticle (Player p) {
        return listener.getPlayersParticle(p);
    }

    private void addParticle(Player player, Particle particle) {
        listener.setPlayersParticle(player, particle);
    }

    private void removeParticle(Player p) {
        listener.removePlayerParticle(p);
    }
}
