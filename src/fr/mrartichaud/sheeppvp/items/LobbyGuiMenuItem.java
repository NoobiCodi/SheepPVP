package fr.mrartichaud.sheeppvp.items;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.utils.AddMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LobbyGuiMenuItem extends SimpleItem {
    public static final int CUSTOM_MODEL_DATA = 5;

    public LobbyGuiMenuItem(SheepPvp sheepPvp) {
        super(
                Material.NETHER_STAR,
                1,
                sheepPvp.configJson.getString("items.lobby_gui_menu_item.title"),
                null,
                null
        );
    }

    @Override
    public ItemStack getItemStack() {
        return AddMeta.addCustomModelData(super.getItemStack(), CUSTOM_MODEL_DATA);
    }
}
