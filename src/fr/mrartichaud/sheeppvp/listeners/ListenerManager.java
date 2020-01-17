package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class ListenerManager implements Listener {
    private SheepPvp sheepPvp;
    private KitsFactory kitsFactory;

    public ListenerManager(SheepPvp sheepPvp, KitsFactory kitsFactory) {
        this.sheepPvp = sheepPvp;
        this.kitsFactory = kitsFactory;
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
        sheepPvp.sheepLogger.info(player.getName() + " joined !");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        new QuitEvent(sheepPvp).onQuit(e);
        e.setQuitMessage(sheepPvp.configJson.getString("msg.bye_msg").replaceAll("%player%", player.getName()));
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
