package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.commands.ClearAllCommand;
import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.permissions.PermissionsManager;
import fr.mrartichaud.sheeppvp.utils.LocationsFunctions;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class ListenerManager implements Listener {
    private SheepPvp sheepPvp;
    private KitsFactory kitsFactory;
    private PermissionsManager permissionsManager;

    public ListenerManager(SheepPvp sheepPvp, KitsFactory kitsFactory) {
        this.sheepPvp = sheepPvp;
        this.kitsFactory = kitsFactory;
        permissionsManager = new PermissionsManager(sheepPvp);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (sheepPvp.authJson.getString(player.getName()) == null) {
            e.setJoinMessage(sheepPvp.configJson.getString("msg.welcome_msg").replaceAll("%player%", player.getName()));
        } else {
            e.setJoinMessage(sheepPvp.configJson.getString("msg.join_msg").replaceAll("%player%", player.getName()));
        }

        new JoinEvent().onJoin(e, sheepPvp);

        permissionsManager.setupPermissions(player);
        sheepPvp.sheepLogger.info(player.getName() + " joined !");
    }

    @EventHandler
    public void onPickItem(EntityPickupItemEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = ((Player) e.getEntity()).getPlayer();

            if (!player.isOp())
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickupArrow(PlayerPickupArrowEvent e) {
        Player player = e.getPlayer();

        if (e.getItem().getItemStack().getType() != Material.TRIDENT)
            if (!e.getPlayer().isOp())
                e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        new QuitEvent(sheepPvp).onQuit(e);
        e.setQuitMessage(sheepPvp.configJson.getString("msg.bye_msg").replaceAll("%player%", player.getName()));
        sheepPvp.sheepLogger.info(player.getName() + " leaved the server !");
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        new InteractEvent(sheepPvp, kitsFactory).onInteract(e);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        new ClickEvent(sheepPvp, kitsFactory).onClick(e);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player victim = e.getEntity().getPlayer();
        Player killer = e.getEntity().getKiller();

        DeathEvent deathEvent = new DeathEvent(e, sheepPvp);

        if (killer != null && killer.getName().equals(victim.getName())) {
            deathEvent.suicide(victim);
        } else if (killer != null) {
            deathEvent.kill(victim, killer);
        } else {
            deathEvent.death(victim);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();

        ClearAllCommand.clearPlayer(player);

        if (PlayerStates.testIfContainsState(sheepPvp, player, PlayerStates.KITPVP))
            e.setRespawnLocation(LocationsFunctions.getLocation(player.getWorld(), sheepPvp, "tps.spawn_kitpvp"));
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (PlayerStates.testIfContainsState(sheepPvp, player, PlayerStates.LOGGING)) {
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (PlayerStates.testIfContainsState(sheepPvp, player, PlayerStates.LOGGING)) {
            e.setCancelled(true);
            return;
        }
    }
}
