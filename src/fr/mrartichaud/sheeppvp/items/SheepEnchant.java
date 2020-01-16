package fr.mrartichaud.sheeppvp.items;

import org.bukkit.enchantments.Enchantment;

public class SheepEnchant {
    private Enchantment enchantment;
    private int level;

    public SheepEnchant(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    public static SheepEnchant fromEnchant() {
        return null;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }
}
