package fr.mrartichaud.sheeppvp.kits;

import org.bukkit.inventory.ItemStack;

public class SheepArmor {
    public ItemStack helmet;
    public ItemStack chestplate;
    public ItemStack leggings;
    public ItemStack boots;
    public ItemStack offhand;

    public SheepArmor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots, ItemStack offhand) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
        this.offhand = offhand;
    }
}
