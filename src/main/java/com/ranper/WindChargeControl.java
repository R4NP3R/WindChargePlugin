package com.ranper;

import com.ranper.commands.WindChargeCommands;
import com.ranper.listeners.WindChargeListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class WindChargeControl extends JavaPlugin {
    WindChargeListener windChargeListener;
    @Override
    public void onEnable() {
        // Plugin startup logic
        windChargeListener = new WindChargeListener();
        getServer().getPluginManager().registerEvents(windChargeListener, this);
        getCommand("windcharge").setExecutor(new WindChargeCommands(windChargeListener));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
