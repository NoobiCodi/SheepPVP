package fr.mrartichaud.sheeppvp.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Map;

public class SheepItem {
    public Material material;
    public int quantity;
    public String name;
    public String[] lore;
    public SheepEnchant[] sheepEnchants;
    public ItemStack itemStack;

    public SheepItem(Material material, int quantity, String name, String[] lore, SheepEnchant[] sheepEnchants) {
        this.material = material;
        this.quantity = quantity;
        this.name = name;
        this.lore = lore;
        this.sheepEnchants = sheepEnchants;

        this.itemStack = new ItemStack(this.material, this.quantity);
        ItemMeta itemMeta = this.itemStack.getItemMeta();

        if (this.name != null) itemMeta.setDisplayName(this.name);
        if (this.lore != null) itemMeta.setLore(Arrays.asList(this.lore));

        if (sheepEnchants != null) {
            for (SheepEnchant sheepEnchant : this.sheepEnchants) {
                itemMeta.addEnchant(sheepEnchant.getEnchantment(), sheepEnchant.getLevel(), true);
            }
        }

        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static SheepItem fromItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
        SheepEnchant[] sheepEnchants = new SheepEnchant[enchants.size()];

        int i = 0;
        for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
            sheepEnchants[i] = new SheepEnchant(entry.getKey(), entry.getValue());
            i++;
        }

        return new SheepItem(
                itemStack.getType(),
                itemStack.getAmount(),
                itemMeta.getDisplayName() == null ? null : itemMeta.getDisplayName(),
                itemMeta.getLore() == null ? null : itemMeta.getLore().toArray(new String[(itemMeta.getLore().size())]),
                sheepEnchants
        );
    }
}
