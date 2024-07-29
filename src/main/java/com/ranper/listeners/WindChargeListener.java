package com.ranper.listeners;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class WindChargeListener implements Listener {

    //variavel da velocidade do windcharge
    private Double newVelocity = 1.0;

    //variavel da força da explosão do windcharge
    private Float newExplosionPower = 1.0f;
    //lista que guardas os players que tem particula
    private HashMap<UUID, Particle> playersParticle = new HashMap<>();

    @EventHandler
    public void setWindChargeSpeed(ProjectileLaunchEvent event) {
        // alteração velocidade do windcharge será apenas para player
        if(event.getEntity().getShooter() instanceof Player) {
            if(event.getEntity() instanceof WindCharge ){
                WindCharge windcharge = (WindCharge) event.getEntity();

                // velocidade atual do windcharge
                Vector velocity = windcharge.getVelocity();
                // multiplicando pela velocidade estipulada pelo player
                velocity.multiply(newVelocity);
                // alterando a aceleração para animação do Windcharge ficar mais fluida
                windcharge.setAcceleration(velocity);
            }
        }
    }

    @EventHandler
    public void setWindChargeStrength(ProjectileHitEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            World world = player.getWorld();
            if(event.getEntity() instanceof WindCharge) {
                WindCharge windcharge = (WindCharge) event.getEntity();

                // como não consegui alterar a força da explosão, decidi fazer o player ficar mais "fraco", então toda vez que um player atirar o
                // WindCharge apenas player irão sentir que a força mudou, para deixar o player mais "fraco", quando atigindo pela WindCharge
                // a velocidade do player será multiplicada pelo oque foi estipulado na variavel newExplosionPower

                        Vector velocity = player.getVelocity();
                        velocity.multiply(newExplosionPower);
                        player.setVelocity(velocity);
                        Particle newParticle = playersParticle.get(player.getUniqueId());

                        // caso o player tenha uma particula ela será spawnada
                        if (newParticle != null) {
                            world.spawnParticle(newParticle, windcharge.getLocation(), 150, 1, 1, 1);
                        }
            }
        }
    }

    public void setNewVelocity(Double newVelocity) {
        this.newVelocity = newVelocity;
    }

    public void setNewExplosionPower(Float newExplosionPower) {
        this.newExplosionPower = newExplosionPower;
    }

    public void setPlayersParticle(Player player, Particle particle) {
        this.playersParticle.put(player.getUniqueId(), particle);
    }

    public Double getNewVelocity() {
        return newVelocity;
    }

    public Float getNewExplosionPower() {
        return newExplosionPower;
    }

    public boolean getPlayersParticle(Player player) {
       return this.playersParticle.containsKey(player.getUniqueId());
    }

    public void removePlayerParticle(Player player) {
        this.playersParticle.remove(player.getUniqueId());
    }

}
