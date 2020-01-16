package fr.mrartichaud.sheeppvp.commands;

import joptsimple.internal.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length < 1) {
            commandSender.sendMessage("Usage: /broadcast <message>");
        } else {
            String sender = commandSender instanceof Player ? commandSender.getName() : "CONSOLE";

            Bukkit.broadcastMessage(MessageFormat.format("§4[{1}]: §a{0}", Strings.join(strings, " "), sender));
        }

        return true;
    }
}
