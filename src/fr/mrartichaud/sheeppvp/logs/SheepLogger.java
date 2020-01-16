package fr.mrartichaud.sheeppvp.logs;

import fr.mrartichaud.sheeppvp.SheepPvp;
import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SheepLogger {
    private String prefix;
    private SheepPvp sheepPvp;

    public SheepLogger(String prefix, SheepPvp sheepPvp) {
        this.prefix = prefix;
        this.sheepPvp = sheepPvp;
    }

    public String getPrefix() {
        String pattern = "MM/dd/yyyy kk:mm:ss";
        return MessageFormat.format("[{0} {2}- {1}]", this.prefix, new SimpleDateFormat(pattern).format(new Date()), ChatColor.WHITE);
    }

    public void info(String msg) {
        this.sheepPvp.getServer().getConsoleSender().sendMessage(MessageFormat.format("{0} {1}INFO - {2}{3}", getPrefix(), ChatColor.GREEN, msg, ChatColor.WHITE));
    }
}
