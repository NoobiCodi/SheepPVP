package fr.mrartichaud.sheeppvp.config.playersdata;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.config.JsonFile;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

public class PlayersDataManager {
    private JsonFile jsonFile;
    private SheepPvp sheepPvp;

    public PlayersDataManager(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
        this.jsonFile = sheepPvp.playersJson;
    }

    public JSONObject createNewInstanceIfNotExists(Player player) {
        if (!testIfInstanceExists(player)) {
            return createNewInstance(player);
        } else
            return jsonFile.get(player.getName());
    }

    public JSONObject createNewInstance(Player player) {
        JSONObject reso = new JSONObject();
        JSONObject rest = new JSONObject();

        rest.put("kdr", 0);
        rest.put("kills", 0);
        rest.put("deaths", 0);

        reso.put("kitpvp", rest);
        reso.put("grade", sheepPvp.configJson.getString("grades.default"));

        jsonFile.getJsonObject().put(player.getName(), reso);
        jsonFile.save();

        return reso;
    }

    public boolean testIfInstanceExists(Player player) {
        if (jsonFile.get(player.getName()) != null)
            return true;
        else
            return false;
    }
}
