package fr.mrartichaud.sheeppvp.items;

import org.bukkit.Material;

public class SimpleItem extends SheepItem {
    public SimpleItem(Material material, int quantity, String name, String[] lore, SheepEnchant[] sheepEnchants) {
        super(material, quantity, name, lore, sheepEnchants);
    }
}
