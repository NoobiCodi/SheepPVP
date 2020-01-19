package fr.mrartichaud.sheeppvp.kits;

import fr.mrartichaud.sheeppvp.items.SheepItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class SheepKit implements Comparable<SheepKit> {
    public String name;
    public String displayName;
    public ItemStack logo;
    public SheepItem[] sheepItems;
    public PotionEffect[] potionEffects;
    public SheepArmor sheepArmor;
    public String classe;

    public SheepKit(String name, String displayName, ItemStack logo, SheepItem[] sheepItems, PotionEffect[] potionEffects, SheepArmor sheepArmor, String classes) {
        this.name = name;
        this.displayName = displayName;
        this.logo = logo;
        this.sheepItems = sheepItems;
        this.potionEffects = potionEffects;
        this.sheepArmor = sheepArmor;
        this.classe = classes;
    }

    @Override
    public int compareTo(SheepKit o) {
        return this.classe.compareToIgnoreCase(o.classe);
    }

    public void equipKit(Player player) {
        for (SheepItem sheepItem : sheepItems) {
            player.getInventory().addItem(sheepItem.itemStack);
        }

        if (sheepArmor != null) {
            if (sheepArmor.helmet != null) player.getInventory().setHelmet(sheepArmor.helmet);
            if (sheepArmor.chestplate != null) player.getInventory().setChestplate(sheepArmor.chestplate);
            if (sheepArmor.leggings != null) player.getInventory().setLeggings(sheepArmor.leggings);
            if (sheepArmor.boots != null) player.getInventory().setBoots(sheepArmor.boots);
            if (sheepArmor.offhand != null) player.getInventory().setItemInOffHand(sheepArmor.offhand);
        }

        if (potionEffects != null) {
            for (PotionEffect potionEffect : potionEffects) {
                player.addPotionEffect(potionEffect);
            }
        }
    }
}
