package fr.mrartichaud.sheeppvp.gui.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoardManager {
    private ScoreboardManager scoreboardManager;

    public ScoreBoardManager() {
        this.scoreboardManager = Bukkit.getServer().getScoreboardManager();
    }

    public void createForPlayer(Player player) {
        createForPlayer(player, false);
    }

    public Scoreboard createForPlayer(Player player, boolean isQuitting) {
        if (scoreboardManager == null) return null;

        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        SheepObjective sheepObjective = new SheepObjective("showhealth", "/ 20 §4♥", "health", DisplaySlot.BELOW_NAME);
        sheepObjective.getObjective(scoreboard);

        player.setScoreboard(scoreboard);
        return scoreboard;
    }
}
