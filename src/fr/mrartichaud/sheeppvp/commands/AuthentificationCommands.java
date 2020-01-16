package fr.mrartichaud.sheeppvp.commands;

import fr.mrartichaud.sheeppvp.SheepPvp;
import fr.mrartichaud.sheeppvp.utils.Encryption;
import fr.mrartichaud.sheeppvp.utils.PlayerStates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthentificationCommands implements CommandExecutor {
    private SheepPvp sheepPvp;

    public AuthentificationCommands(SheepPvp sheepPvp) {
        this.sheepPvp = sheepPvp;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (!PlayerStates.testIfContainsState(sheepPvp, player, PlayerStates.LOGGING)) {
                player.sendMessage(sheepPvp.configJson.getString("auth.msg.already_login"));
                return true;
            }

            if (s.equalsIgnoreCase("login")) {
                String usage = sheepPvp.configJson.getString("auth.msg.login_usage");

                if (strings.length != 1) {
                    player.sendMessage(usage);
                    return true;
                }

                String pas = strings[0];

                if (sheepPvp.authJson.getString(player.getName()) == null) {
                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.notlogin_register"));
                    return true;
                }

                String encryptedPassword = sheepPvp.authJson.getString(player.getName());
                String decryptedPassword = Encryption.decrypt(encryptedPassword, Encryption.key);

                if (pas.equals(decryptedPassword)) {
                    sheepPvp.playersData.get(player.getName()).put("game", PlayerStates.LOBBY);
                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.success_login"));
                    return true;
                } else {
                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.wrong_password"));
                    return true;
                }
            } else if (s.equalsIgnoreCase("register")) {
                String usage = sheepPvp.configJson.getString("auth.msg.register_usage");

                if (sheepPvp.authJson.getString(player.getName()) != null) {
                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.already_register"));
                    return true;
                }

                if (strings.length != 2) {
                    player.sendMessage(usage);
                    return true;
                }

                String firstPas = strings[0];
                String secondPas = strings[1];

                int limitMax = sheepPvp.configJson.getInt("auth.msg.max_chars");
                int limitMin = sheepPvp.configJson.getInt("auth.msg.min_chars");

                if (!firstPas.equals(secondPas)) {
                    player.sendMessage(usage);
                    return true;
                } else if (firstPas.length() > limitMax) {
                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.too_big"));
                    return true;
                } else if (firstPas.length() < limitMin) {
                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.too_small"));
                    return true;
                } else {
                    String encryptedPassword = Encryption.encrypt(firstPas, Encryption.key);

                    sheepPvp.authJson.getJsonObject().put(player.getName(), encryptedPassword);
                    sheepPvp.authJson.save();

                    sheepPvp.playersData.get(player.getName()).put("game", PlayerStates.LOBBY);

                    player.sendMessage(sheepPvp.configJson.getString("auth.msg.success_register"));
                    return true;
                }
            }
        } else {
            commandSender.sendMessage(sheepPvp.configJson.getString("auth.msg.only_player"));
        }

        return true;
    }
}
