package me.harry.eggbazooka;

import me.harry.eggbazooka.listener.BazookaListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EggBazooka extends JavaPlugin {

    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new BazookaListener(), this);

    }
    public void onDisable() {

    }
}
