package me.harry.eggbazooka.commands;

import me.harry.eggbazooka.EggBazooka;
import me.harry.eggbazooka.utils.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GiveBazookaCommand implements CommandExecutor, TabCompleter {

    private final EggBazooka plugin;

    public GiveBazookaCommand(EggBazooka plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("egg.give")) {
                Player target = null;
                if (args.length > 0) {
                    target = plugin.getServer().getPlayer(args[0]);
                }
                if (target == null) {
                    target = player;
                }

                ItemStack bazooka = Utils.makeItem(new ItemStack(Material.IRON_SHOVEL), 1, "&b&lEGG BAZOOKA", true, true,
                        "", "&fRight Click to shoot!", "");
                ItemMeta bazookaMeta = bazooka.getItemMeta();
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("rapideggbazooka")) {
                        bazookaMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "bazooka"), PersistentDataType.STRING, "bazooka");
                        bazookaMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "rapid"), PersistentDataType.STRING, "rapid");
                    } else if (args[0].equalsIgnoreCase("eggbazooka")) {
                        bazookaMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "type"), PersistentDataType.STRING, "bazooka");
                    }
                }
                bazooka.setItemMeta(bazookaMeta);
                target.getInventory().addItem(bazooka);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;

        if (args.length == 1) {
            if (player.hasPermission("egg.give")) {
                completions.add("eggbazooka");
                completions.add("rapideggbazooka");
            }

            return completions;
        }
        return completions;
    }
}
