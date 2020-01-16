package fr.mrartichaud.sheeppvp.gui.menus.lobbyMenu;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.items.SimpleItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class LobbyMenu {
    private SheepPvp sheepPvp;
    public HashMap<ItemStack, LobbyItemFunction> itemFunction;

    public LobbyMenu(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
        this.itemFunction = new HashMap<>();

        this.itemFunction.put(new SimpleItem(Material.IRON_SWORD, 1, "Kit PVP", new String[]{ "Combat danguereux" }, null).getItemStack(), new KitPvpLobbyItem());
    }

    public void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, sheepPvp.configJson.getString("guis.menus.lobby_menu.title"));

        int i = 0;

        for (HashMap.Entry<ItemStack, LobbyItemFunction> entry : itemFunction.entrySet()) {
            inventory.setItem(i, new ItemStack(entry.getKey()));
            i++;
        }

        player.openInventory(inventory);
    }

    private Void kitPvp() {
        System.out.println();
        return null;
    }
}
