package fr.mrartichaud.sheeppvp.listeners;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.commands.ClearAllCommand;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.text.MessageFormat;

public class DeathEvent {
    private SheepPvp sheepPvp;
    private PlayerDeathEvent e;

    public DeathEvent(PlayerDeathEvent e, SheepPvp sheepPvp) {
        this.e = e;
        this.sheepPvp = sheepPvp;
    }

    public void kill(Player v, Player k) {
        e.setDeathMessage(MessageFormat.format("{0} was killed by {1}", v.getName(), k.getName()));
    }

    public void death(Player d) {
        e.setDeathMessage(MessageFormat.format("{0} is dead", d.getName()));
    }

    public void suicide(Player d) {
        e.setDeathMessage(MessageFormat.format("{0} suicide himself", d.getName()));
    }
}
