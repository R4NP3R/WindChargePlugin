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
                // Opção para aumentar a velocidade do windcharge, player pode usar velocity ou speed, /windcharge <speed/velocity>
                if(args[0].equalsIgnoreCase("velocity") || args[0].equalsIgnoreCase("speed")) {
                    //se o player não inserir um número ocorre uma exception e tratamos elas retornando uma mensagem para o player
                    try {
                        // caso o player digite apenas /windcharge <velocity/speed>, ira retornar a velocidade atual do WindCharge
                        if(args.length < 2) {
                            String velocity = listener.getNewVelocity().toString();
                            player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.AQUA + "speed is: " + ChatColor.WHITE + velocity);
                            return false;
                        }
                        // atualiza o valor da velocidade do windcharge e retorna uma mensagem para o player
                        Double newVelocity = Double.parseDouble(args[1]);
                        player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.AQUA + "speed: " + ChatColor.WHITE +  args[1] + "," + ChatColor.GREEN + " has been set");
                        this.listener.setNewVelocity(newVelocity);
                        return false;
                    } catch (Exception e) {
                        // mensagem retornada caso o player insira não insira um numero
                        player.sendMessage(ChatColor.RED + "* Please insert a number");
                    }
                }
                // Opção para aumentar a força do windcharge
                if(args[0].equalsIgnoreCase("power")) {
                    //se o player não inserir um número ocorre uma exception e tratamos elas retornando uma mensagem para o player
                    try {
                        // caso o player digite apenas /windcharge power, ira retornar a força atual do WindCharge
                        if(args.length < 2) {
                            String power = listener.getNewExplosionPower().toString();
                            player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.RED + "power is: " + ChatColor.WHITE + power);
                            return false;
                        }
                        // atualiza o força da velocidade do windcharge e retorna uma mensagem para o player
                        float newVelocity = Float.parseFloat(args[1]);
                        player.sendMessage(ChatColor.BLUE + "* Windcharge " + ChatColor.RED + "power: " + ChatColor.WHITE +  args[1] + "," + ChatColor.GREEN + " has been set");
                        this.listener.setNewExplosionPower(newVelocity);
                        return false;
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "* Please insert a number");
                    }
                }
                if(args[0].equalsIgnoreCase("particles")) {
                    // caso o player digite apenas /windcharge particles, ira retornar uma mensagem do uso correto do comando
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles list");
                        player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles <particle>");
                        player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles off");
                        return false;
                    }
                    // chama uma função que lista todas as particulas presentes no minecraft e retorna em uma mensagem para o player escolher uma particula
                    if (args[1].equalsIgnoreCase("list")) {
                        listParticles(player);
                        return false;
                    }
                    // retira a particula que o player escolheu
                    if (args[1].equalsIgnoreCase("off")) {
                        // confere se o player já tem particula
                        if (hasParticle(player)) {
                            //caso tenha remove dele
                            removeParticle(player);
                            player.sendMessage(ChatColor.AQUA + "Particles off");
                        } else {
                            // caso não tenha retorna que o player que as particulas já estão desativadas.
                            player.sendMessage(ChatColor.RED + "You windcharge particles are off");
                        }
                        return false;
                    }
                    // caso o terceiro paramatro dentro de /windcharge particles não seja list ou off, será reconhecido como nome de uma particula.
                    //se o player digitar um particula que não existe, irá cair no catch e retornara uma mensagem ao player.
                    try {
                        // se a particula existir vai retornar uma mensagem de sucesso
                        Particle e = Particle.valueOf(args[1].toUpperCase());
                        addParticle(player, e);
                        player.sendMessage(ChatColor.GREEN + "Particle " + e.name().toLowerCase() + " has been set");
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Invalid Particle: " + args[1].toLowerCase());
                        player.sendMessage(ChatColor.RED + "Use: /home particle list");
                    }
                }
                // Opção que retorna uma mensagem para ajudar o player a entender melhor o comando /windcharge
                if(args[0].equalsIgnoreCase("help")) {
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge velocity/speed <speed>" , ChatColor.AQUA +"(To change speed of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge power <strength>", ChatColor.AQUA + "(To change explosion strength of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge particles <particle>", ChatColor.AQUA + "(To change particles of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge velocity/speed", ChatColor.AQUA + "(To get speed of windcharge)");
                    player.sendMessage(ChatColor.GOLD + "Use: /windcharge power", ChatColor.AQUA + "(To get explosion strength of windcharge)");
                }
                //caso o player entenda errado e digite /windcharge velocity/speed, retornara uma mensagem mostrando o uso correto
                if(args[0].equalsIgnoreCase("velocity/speed")) {
                    player.sendMessage(ChatColor.GOLD + "Please use: /windcharge velocity or /windcharge speed");
                }
            } else {
                // se o player digitar apenas /windcharge retornara essa mensagem para guiar o player
                player.sendMessage(ChatColor.GOLD + "Use: /windcharge help");
            }
        } else {
            // caso algo que não seja o player use o comando
            sender.sendMessage(ChatColor.RED + "/windcharge is a player command only!");
        }
        return false;
    }

    // função que retorna as listas de particulas
    private void listParticles (Player p) {
        String text = "";

        //cada pa é o nome de uma particula
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
