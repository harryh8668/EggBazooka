package me.harry.eggbazooka.listener;

import me.harry.eggbazooka.EggBazooka;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;

public class BazookaListener implements Listener {

    private final EggBazooka plugin;

    public BazookaListener(EggBazooka plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBazooka(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().has(new NamespacedKey(plugin, "bazooka"), PersistentDataType.STRING)) {
            if (event.getItem() != null && event.getItem().getType() == Material.IRON_SHOVEL) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (checkAmmo(event.getPlayer(), new ItemStack(Material.EGG))) {
                        if (getAmmo(event.getPlayer(), new ItemStack(Material.EGG)) > 0) {
                            org.bukkit.entity.Egg egg = event.getPlayer().launchProjectile(org.bukkit.entity.Egg.class);
                            egg.setMetadata("explosive", new FixedMetadataValue(plugin, true));
                            removeAmmo(event.getPlayer(), new ItemStack(Material.EGG));
                        }
                    } else {
                        event.getPlayer().sendMessage("Out of ammo");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEggLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Egg) {
            org.bukkit.entity.Egg egg = (org.bukkit.entity.Egg) event.getEntity();
            if (egg.hasMetadata("explosive") && egg.getMetadata("explosive").get(0).asBoolean()) {
                egg.getWorld().createExplosion(egg.getLocation(), 4.0F);
            }
        }
    }

    public static boolean checkAmmo(Player player, ItemStack ammo) {
        if (player.hasPermission("egg.inf")) {
            return true;
        }
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == ammo.getType()) {
                return true;
            }
        }
        return false;
    }

    public static int getAmmo(Player player, ItemStack ammo) {
        if (player.hasPermission("egg.inf")) {
            return 1;
        }
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == ammo.getType()) {
                return player.getInventory().getItem(i).getAmount();
            }
        }
        return 0;
    }

    public static void removeAmmo(Player player, ItemStack ammo) {
        if (player.hasPermission("egg.inf")) {
            return;
        }
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
