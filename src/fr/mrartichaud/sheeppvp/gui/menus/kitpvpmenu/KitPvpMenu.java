package fr.mrartichaud.sheeppvp.gui.menus.kitpvpmenu;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.kits.SheepClasse;
import fr.mrartichaud.sheeppvp.kits.SheepKit;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class KitPvpMenu {
    private SheepPvp sheepPvp;
    private KitsFactory kitsFactory;

    public KitPvpMenu(SheepPvp sheepPvp, KitsFactory kitsFactory) {
        this.sheepPvp = sheepPvp;
        this.kitsFactory = kitsFactory;
    }

    private ItemStack getDecoration(boolean left, String name) {
        ItemStack itemStack = new ItemStack(Material.BIRCH_SIGN);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (left) itemMeta.setDisplayName(name + " ->");
        else itemMeta.setDisplayName("<- " + name);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, sheepPvp.configJson.getString("guis.menus.kitpvp_menu.title"));

        int i = 0;

        Collections.sort(kitsFactory.sheepClasses, Collections.reverseOrder());

        for (SheepClasse sheepClasse : kitsFactory.sheepClasses) {
            inventory.setItem((i * 9), getDecoration(true, sheepClasse.displayName));
            inventory.setItem(8 + (i * 9), getDecoration(false, sheepClasse.displayName));

            int j = 0;

            Collections.sort(Arrays.asList(sheepClasse.sheepKits), Collections.reverseOrder());

            for (SheepKit sheepKit : sheepClasse.sheepKits) {
                inventory.setItem(j + 1 + (i * 9), sheepKit.logo);

                j++;
            }

            i++;
        }

        player.openInventory(inventory);
    }
}
