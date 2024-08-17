package me.harry.eggbazooka;

import me.harry.eggbazooka.commands.GiveBazookaCommand;
import me.harry.eggbazooka.listener.BazookaListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EggBazooka extends JavaPlugin {

    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new BazookaListener(this), this);
        getCommand("givebazooka").setExecutor(new GiveBazookaCommand(this));

    }
    public void onDisable() {

    }
}
