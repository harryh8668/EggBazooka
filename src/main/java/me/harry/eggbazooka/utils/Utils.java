package me.harry.eggbazooka.utils;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String colorCodes(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static ItemStack makeItem(ItemStack itemStack, Integer amount, String name, boolean enchanted, boolean hideflags, @Nullable String... lore) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(colorCodes(name));

        if (enchanted) {
            itemMeta.addEnchant(Enchantment.LOYALTY, 1, true);
        }


        if (lore != null) {
            List<String> loreComponents = new ArrayList<>();
            for (String line : lore) {
                loreComponents.add(colorCodes(line));
            }
            itemMeta.setLore(loreComponents);
        }


        if (hideflags) {
            for (ItemFlag flag : ItemFlag.values()) {
                itemMeta.addItemFlags(flag);
            }
        }

        itemStack.setItemMeta(itemMeta);
        itemStack.setAmount(amount);

        return itemStack;
    }
}
