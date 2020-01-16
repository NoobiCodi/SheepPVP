package fr.mrartichaud.sheeppvp.commands;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

public class ClearLagCommand implements CommandExecutor {
    private SheepPvp sheepPvp;

    public ClearLagCommand(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        int howmany = clearLag(sheepPvp);

        commandSender.sendMessage(sheepPvp.configJson.getString("clearlag.msg_player").replaceAll("%items%", String.valueOf(howmany)));
        return true;
    }

    public static int clearLag(SheepPvp sheepPvp) {
        World world = Bukkit.getWorld(sheepPvp.configJson.getString("clearlag.world"));
        List<Entity> entityList = world.getEntities();

        int i = 0;

        for (Entity entity : entityList) {
            if (entity.getType() == EntityType.DROPPED_ITEM) {
                entity.remove();
                i++;
            }
        }

        String msg = sheepPvp.configJson.getString("clearlag.msg_global").replaceAll("%items%", String.valueOf(i));

        Bukkit.broadcastMessage(msg);
        return i;
    }

    public static void startClearLag(SheepPvp main) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            @Override
            public void run() {
                clearLag(main);
            }
        }, 1L, (long) 20 * (main.configJson.getInt("clearlag.interval_in_seconds")));
    }
}
