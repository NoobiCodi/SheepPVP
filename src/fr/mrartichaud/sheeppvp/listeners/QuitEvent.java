package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent {
    private SheepPvp sheepPvp;

    public QuitEvent(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
    }

    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        sheepPvp.playersData.remove(player.getName());

        for (Player p : Bukkit.getOnlinePlayers()) {
            sheepPvp.scoreBoardManager.createForPlayer(p, true);
        }
    }
}
