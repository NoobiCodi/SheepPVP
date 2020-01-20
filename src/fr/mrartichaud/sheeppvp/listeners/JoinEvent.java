package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.commands.ClearAllCommand;
import fr.mrartichaud.sheeppvp.items.LobbyGuiMenuItem;
import fr.mrartichaud.sheeppvp.utils.LocationsFunctions;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

public class JoinEvent {
    public void onJoin(PlayerJoinEvent e, SheepPvp sheepPvp) {
        Player player = e.getPlayer();
        JSONObject playerData = new JSONObject();
        ClearAllCommand.clearPlayer(player);

        playerData.put("game", PlayerStates.LOGGING);

        sheepPvp.playersData.put(player.getName(), playerData);
        sheepPvp.playersDataManager.createNewInstanceIfNotExists(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!PlayerStates.testIfContainsState(sheepPvp, player, PlayerStates.LOGGING)) {
                    cancel();
                }

                if (!isCancelled()) {
                    if (sheepPvp.authJson.getString(player.getName()) == null) {
                        player.sendMessage(sheepPvp.configJson.getString("auth.msg.spam_register"));
                    } else {
                        player.sendMessage(sheepPvp.configJson.getString("auth.msg.spam_login"));
                    }
                }
            }
        }.runTaskTimer(sheepPvp, 20L, 100);

        LobbyGuiMenuItem lobbyGuiMenuItem = new LobbyGuiMenuItem(sheepPvp);
        ItemStack lgmItemStack = lobbyGuiMenuItem.getItemStack();

        player.getInventory().setItem(4, lgmItemStack);

        Location spawnLocation = LocationsFunctions.getLocation(player.getWorld(), sheepPvp, "tps.spawn_lobby");

        player.teleport(spawnLocation);
    }
}
