package fr.mrartichaud.sheeppvp.kits;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.items.SheepEnchant;
import fr.mrartichaud.sheeppvp.items.SheepItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitsFactory {
    private SheepPvp sheepPvp;

    public static final Map<String, Color> COLORS = new HashMap<String, Color>() {{
        put("red", Color.RED);
        put("black", Color.BLACK);
    }};

    public List<SheepClasse> sheepClasses = new ArrayList<>();

    public KitsFactory(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
    }

    public void factorate() {
        JSONObject classes = sheepPvp.configJson.get("kitpvp_classique.classes");

        classes.forEach((key, val) -> {
            JSONObject value = (JSONObject) val;
            List<SheepKit> kits = new ArrayList<>();

            ((JSONObject) value.get("kits")).forEach((kkey, kval) -> {
                JSONObject kvalue = (JSONObject) kval;

                String kitDisplayName = (String) kvalue.get("displayName");
                String kitName = (String) kvalue.get("name");
                ItemStack logo = factoreItem((JSONObject) kvalue.get("logo"));
                JSONObject sheepArmors = kvalue.get("sheepArmor") == null ? null : (JSONObject) kvalue.get("sheepArmor");
                List<SheepItem> items = new ArrayList<>();
                JSONArray itemsJSON = (JSONArray) kvalue.get("items");
                JSONArray potionsEffectJSON = kvalue.get("potionEffects") == null ? null : (JSONArray) kvalue.get("potionEffects");
                SheepArmor sheepArmor = null;
                List<PotionEffect> potionEffects = new ArrayList<>();

                for (Object o : itemsJSON) {
                    items.add(SheepItem.fromItemStack(factoreItem((JSONObject) o)));
                }

                if (sheepArmors != null) {
                    JSONObject helmet = sheepArmors.get("helmet") == null ? null : (JSONObject) sheepArmors.get("helmet");
                    JSONObject chestplate = sheepArmors.get("chestplate") == null ? null : (JSONObject) sheepArmors.get("chestplate");
                    JSONObject leggings = sheepArmors.get("leggings") == null ? null : (JSONObject) sheepArmors.get("leggings");
                    JSONObject boots = sheepArmors.get("helmet") == null ? null : (JSONObject) sheepArmors.get("boots");
                    JSONObject offhand = sheepArmors.get("offhand") == null ? null : (JSONObject) sheepArmors.get("offhand");

                    sheepArmor = new SheepArmor(factoreItem(helmet), factoreItem(chestplate), factoreItem(leggings), factoreItem(boots), factoreItem(offhand));
                }

                if (potionsEffectJSON != null) {
                    for (Object o : potionsEffectJSON) {
                        JSONObject obj = (JSONObject) o;

                        PotionEffectType potionEffectType = PotionEffectType.getByName((String) obj.get("type"));
                        int duration = Math.toIntExact((Long) obj.get("duration"));
                        int amp = Math.toIntExact((Long) obj.get("amp")) - 1;

                        potionEffects.add(new PotionEffect(potionEffectType, duration, amp));
                    }
                }

                kits.add(new SheepKit(
                        kitName,
                        kitDisplayName,
                        logo,
                        items.toArray(new SheepItem[items.size()]),
                        potionEffects.size() == 0 ? null : potionEffects.toArray(new PotionEffect[potionEffects.size()]),
                        sheepArmor,
                        (String) key
                ));
            });

            SheepClasse classe = new SheepClasse((String) value.get("name"), (String) value.get("displayName"), kits.toArray(new SheepKit[kits.size()]));
            sheepClasses.add(classe);
        });
    }

    public ItemStack factoreItem(JSONObject obj) {
        if (obj == null || obj.size() == 0) return null;

        String name = obj.get("name") == null ? null : (String) obj.get("name");

        JSONArray loreJSON = (JSONArray) obj.get("lore");
        List<String> lore = null;

        if (loreJSON != null) {
             lore = new ArrayList<>();
            for (Object o : loreJSON) {
                lore.add((String) ((JSONObject) o).get("name"));
            }
        }

        String item = (String) obj.get("item");
        int quantity = obj.get("quantity") == null ? 1 : Math.toIntExact((Long) obj.get("quantity"));

        ItemStack itemStack = new ItemStack(Material.valueOf(item), quantity);
        ItemMeta itemMeta = itemStack.getItemMeta();

        JSONArray enchantsJSON = (JSONArray) obj.get("enchants");

        if (enchantsJSON != null) {
            for (int i = 0; i < enchantsJSON.size(); i++){
                JSONObject el = (JSONObject) enchantsJSON.get(i);

                SheepEnchant sheepEnchant = new SheepEnchant(Enchantment.getByKey(NamespacedKey.minecraft((String) el.get("enchant"))), Math.toIntExact((Long) el.get("level")));
                itemMeta.addEnchant(sheepEnchant.getEnchantment(), sheepEnchant.getLevel(), true);
            }
        }

        if (name != null) itemMeta.setDisplayName(name);
        if (lore != null) itemMeta.setLore(lore);

        itemStack.setItemMeta(itemMeta);

        String color = (String) obj.get("color");

        if (color != null) {
            LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
            leatherArmorMeta.setColor(COLORS.get(color));
            itemStack.setItemMeta(leatherArmorMeta);
        }

        return itemStack;
    }
}
