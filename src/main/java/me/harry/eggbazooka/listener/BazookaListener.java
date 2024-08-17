package me.harry.eggbazooka.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BazookaListener implements Listener {

    @EventHandler
    public void onBazooka(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.IRON_SHOVEL) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (checkAmmo(event.getPlayer(), new ItemStack(Material.EGG))) {
                    if (getAmmo(event.getPlayer(), new ItemStack(Material.EGG)) > 0) {
                        event.getPlayer().launchProjectile(org.bukkit.entity.Egg.class);
                        removeAmmo(event.getPlayer(), new ItemStack(Material.EGG));
                    }
                } else {
                    event.getPlayer().sendMessage("Out of ammo");
                }
            }
        }
    }

    public static boolean checkAmmo(Player player, ItemStack ammo) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == ammo.getType()) {
                return true;
            }
        }
        return false;
    }

    public static int getAmmo(Player player, ItemStack ammo) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == ammo.getType()) {
                return player.getInventory().getItem(i).getAmount();
            }
        }
        return 0;
    }

    public static void removeAmmo(Player player, ItemStack ammo) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == ammo.getType()) {
                int amount = player.getInventory().getItem(i).getAmount();
                if (amount > 1) {
                    player.getInventory().getItem(i).setAmount(amount - 1);
                } else {
                    player.getInventory().setItem(i, null);
                }
                break;
            }
        }
    }
}
