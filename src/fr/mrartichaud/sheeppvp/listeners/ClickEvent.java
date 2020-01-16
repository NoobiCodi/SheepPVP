package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.gui.menus.kitpvpmenu.KitPvpMenuClicked;
import fr.mrartichaud.sheeppvp.gui.menus.lobbyMenu.LobbyMenu;
import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.utils.Args;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ClickEvent {
    private SheepPvp sheepPvp;
    private KitsFactory kitsFactory;

    public ClickEvent(SheepPvp sheepPvp, KitsFactory kitsFactory) {
        this.sheepPvp = sheepPvp;
        this.kitsFactory = kitsFactory;
    }

    public void onClick(InventoryClickEvent e) {
        String menuTitle = e.getView().getTitle();
        ItemStack itemClicked = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();

        if (itemClicked == null) return;

        if (menuTitle.equalsIgnoreCase(sheepPvp.configJson.getString("guis.menus.lobby_menu.title"))) {
            e.setCancelled(true);

            LobbyMenu lobbyMenu = new LobbyMenu(sheepPvp);

            if (lobbyMenu.itemFunction.containsKey(itemClicked)) {
                try {
                    lobbyMenu.itemFunction.get(itemClicked).call(new Args(player, sheepPvp));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (menuTitle.equalsIgnoreCase(sheepPvp.configJson.getString("guis.menus.kitpvp_menu.title"))) {
            e.setCancelled(true);
            KitPvpMenuClicked.clicked(e, kitsFactory);
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 10, 0.94f);
        }
    }
}
