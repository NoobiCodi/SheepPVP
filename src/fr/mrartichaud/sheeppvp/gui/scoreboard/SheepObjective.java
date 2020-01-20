package fr.mrartichaud.sheeppvp.gui.scoreboard;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class SheepObjective {
    private String name;
    private String displayName;
    private String id;
    private DisplaySlot displaySlot;

    public SheepObjective(String name, String displayName, String id, DisplaySlot displaySlot) {
        this.name = name;
        this.displayName = displayName;
        this.id = id;
        this.displaySlot = displaySlot;
    }

    public Objective getObjective(Scoreboard scoreboard) {
        Objective objective = scoreboard.registerNewObjective(name, id, id);
        objective.setDisplaySlot(displaySlot);
        objective.setDisplayName(displayName);

        return objective;
    }
}
