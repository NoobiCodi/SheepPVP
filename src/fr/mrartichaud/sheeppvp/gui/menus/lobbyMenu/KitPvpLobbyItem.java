package fr.mrartichaud.sheeppvp.gui.menus.lobbyMenu;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.commands.ClearAllCommand;
import fr.mrartichaud.sheeppvp.items.KitPvpMenuItem;
import fr.mrartichaud.sheeppvp.utils.Args;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class KitPvpLobbyItem implements LobbyItemFunction {
    @Override
    public void call(Args args) {
        Player player = null;
        SheepPvp sheepPvp = null;

        for (Object arg : args.args) {
            if (arg instanceof Player) player = (Player) arg;
            if (arg instanceof SheepPvp) sheepPvp = (SheepPvp) arg;
        }

        if (player != null && sheepPvp != null) {
            try {
                sheepPvp.playersData.get(player.getName()).put("game", PlayerStates.KITPVP);
            } catch (Exception err) {
                player.sendMessage("An error occured, please try to rejoin the server !");
            }

            Location spawnLocation = new Location(
                    player.getWorld(),
                    sheepPvp.configJson.getInt("tps.spawn_kitpvp.x"),
                    sheepPvp.configJson.getInt("tps.spawn_kitpvp.y"),
                    sheepPvp.configJson.getInt("tps.spawn_kitpvp.z")
            );

            player.teleport(spawnLocation);
            ClearAllCommand.clearPlayer(player);

            player.getInventory().setItem(4, new KitPvpMenuItem(sheepPvp).getItemStack());
        }
    }
}
