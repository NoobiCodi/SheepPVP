package fr.mrartichaud.sheeppvp.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AddMeta {
    public static ItemStack addCustomModelData(ItemStack item, int i) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setCustomModelData(i);
        item.setItemMeta(itemMeta);
        return item;
    }
}
