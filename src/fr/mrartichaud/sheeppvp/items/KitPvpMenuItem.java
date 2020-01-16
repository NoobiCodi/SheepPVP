package fr.mrartichaud.sheeppvp.items;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.utils.AddMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitPvpMenuItem extends SimpleItem {
    public static final int CUSTOM_MODEL_DATA = 6;

    public KitPvpMenuItem(SheepPvp sheepPvp) {
        super(
                Material.COMPASS,
                1,
                sheepPvp.configJson.getString("items.spawn_gui_menu_item.title"),
                null,
                null
        );
    }

    @Override
    public ItemStack getItemStack() {
        return AddMeta.addCustomModelData(super.getItemStack(), CUSTOM_MODEL_DATA);
    }
}
