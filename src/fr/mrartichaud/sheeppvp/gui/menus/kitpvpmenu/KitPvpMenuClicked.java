package fr.mrartichaud.sheeppvp.gui.menus.kitpvpmenu;

import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.kits.SheepClasse;
import fr.mrartichaud.sheeppvp.kits.SheepKit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitPvpMenuClicked {
     public static void clicked(InventoryClickEvent e, KitsFactory kitsFactory) {
         SheepClasse sheepClasse = kitsFactory.sheepClasses.get((int) Math.floor(e.getSlot() / 9f));
         SheepKit[] sheepKits = sheepClasse.sheepKits;
         ItemStack itemClicked = e.getCurrentItem();
         Player player = (Player) e.getWhoClicked();

         SheepKit kit = null;

         for (SheepKit sheepKit : sheepKits) {
             if ((sheepKit.logo.getType() == itemClicked.getType()) && (sheepKit.logo.getItemMeta().getDisplayName().equals(itemClicked.getItemMeta().getDisplayName()))) {
                 kit = sheepKit;
             }
         }

         if (kit == null) return;

         kit.equipKit(player);
    }
}
