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
    private Double newVelocity = 1.0;
    private Float newExplosionPower = 1.0f;
    private HashMap<UUID, Particle> playersParticle = new HashMap<>();

    @EventHandler
    public void setWindChargeSpeed(ProjectileLaunchEvent event) {
        if(event.getEntity().getShooter() instanceof Player) {
            if(event.getEntity() instanceof WindCharge ){
                WindCharge windcharge = (WindCharge) event.getEntity();

                Vector velocity = windcharge.getVelocity();
                velocity.normalize();
                velocity.multiply(newVelocity);
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

                        windcharge.setIsIncendiary(true);
                        Vector velocity = player.getVelocity();
                        velocity.multiply(newExplosionPower);
                        player.setVelocity(velocity);
                        Particle newParticle = playersParticle.get(player.getUniqueId());

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
