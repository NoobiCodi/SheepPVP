package fr.mrartichaud.sheeppvp.utils;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationsFunctions {
    public static Location getLocation(World world, SheepPvp sheepPvp, String path) {
        Location location = new Location(
                world,
                sheepPvp.configJson.getInt(path + ".x"),
                sheepPvp.configJson.getInt(path + ".y"),
                sheepPvp.configJson.getInt(path + ".z")
        );

        return location;
    }
}
