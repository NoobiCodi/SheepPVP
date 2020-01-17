package fr.mrartichaud.sheeppvp.permissions;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PermissionsManager {
    private SheepPvp sheepPvp;

    public PermissionsManager(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
    }

    public void setupPermissions(Player player) {
        PermissionAttachment attachment = player.addAttachment(sheepPvp);

        sheepPvp.playerPermissions.put(player.getUniqueId(), attachment);
        permissionsSetter(player.getUniqueId());
    }

    private void permissionsSetter(UUID uuid) {
        PermissionAttachment attachment = sheepPvp.playerPermissions.get(uuid);

        List<JSONObject> grades = new ArrayList<>();
        final String[] defaultGrades = { null };

        ((JSONObject) sheepPvp.configJson.get("grades")).forEach((key, val) -> {
            if (key.equals("default")) {
                defaultGrades[0] = (String) key;
            } else {
                grades.add((JSONObject) val);
            }
        });

        if (defaultGrades[0] == null ) {
            Bukkit.broadcastMessage("ALERT: PERMISSION NOT PROPERLY SET, NO DEFAULT GRADE");
            return;
        }

        String defaultGrade = defaultGrades[0];

        for (JSONObject grade : grades) {
            JSONArray permsJSON = (JSONArray) grade.get("perms");
            List<String> perms = new ArrayList<>();
            String name = (String) grade.get("name");

            for (Object o : permsJSON) {
                perms.add((String) o);
            }

            if (name.equals("agneaux")) {
                for (String perm : perms) {
                    attachment.setPermission(perm, true);
                }
            }
        }
    }
}
