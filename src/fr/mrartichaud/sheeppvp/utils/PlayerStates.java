package fr.mrartichaud.sheeppvp.utils;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.bukkit.entity.Player;

public class PlayerStates {
    public static final int LOGGING = 0;
    public static final int LOBBY = 1;
    public static final int KITPVP = 2;

    public static boolean testIfContainsState(SheepPvp sheepPvp, Player player, int state) {
        if (sheepPvp.playersData.containsKey(player.getName())) {
            if (sheepPvp.playersData.get(player.getName()) != null &&
                    (int) sheepPvp.playersData.get(player.getName()).get("game") == state
            ) {
                return true;
            }
        }

        return false;
    }
}
