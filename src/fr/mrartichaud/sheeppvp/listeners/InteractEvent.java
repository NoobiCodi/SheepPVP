package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.gui.menus.kitpvpmenu.KitPvpMenu;
import fr.mrartichaud.sheeppvp.gui.menus.lobbyMenu.LobbyMenu;
import fr.mrartichaud.sheeppvp.items.KitPvpMenuItem;
import fr.mrartichaud.sheeppvp.items.LobbyGuiMenuItem;
import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent {
    private SheepPvp sheepPvp;
    private KitsFactory kitsFactory;

    public InteractEvent(SheepPvp sheepPvp, KitsFactory kitsFactory) {
        this.sheepPvp = sheepPvp;
        this.kitsFactory = kitsFactory;
    }

    public void onInteract(PlayerInteractEvent e) {
        ItemStack itemStack = e.getItem();
        Player player = e.getPlayer();

        if (PlayerStates.testIfContainsState(sheepPvp, player, PlayerStates.LOGGING)) {
            e.setCancelled(true);
            return;
        }

        if (itemStack != null) {
            if (testTypeModelData(itemStack, Material.NETHER_STAR, LobbyGuiMenuItem.CUSTOM_MODEL_DATA)) {
                new LobbyMenu(sheepPvp).openMenu(player);
            } else if (testTypeModelData(itemStack, Material.COMPASS, KitPvpMenuItem.CUSTOM_MODEL_DATA)) {
                new KitPvpMenu(sheepPvp, kitsFactory).openMenu(player);
            }
        }
    }

    private boolean testTypeModelData(ItemStack itemStack, Material material, int customModelData) {
        if (itemStack.getType() == material && itemStack.hasItemMeta()) {
            if (itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == customModelData) {
                return true;
            }
        }

        return false;
    }
}
