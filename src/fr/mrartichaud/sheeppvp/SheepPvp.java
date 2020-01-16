package fr.mrartichaud.sheeppvp;

import fr.mrartichaud.sheeppvp.commands.*;
import fr.mrartichaud.sheeppvp.config.JsonFile;
import fr.mrartichaud.sheeppvp.kits.KitsFactory;
import fr.mrartichaud.sheeppvp.listeners.ListenerManager;
import fr.mrartichaud.sheeppvp.logs.SheepLogger;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.HashMap;

public class SheepPvp extends JavaPlugin {
    public String prefix;
    public SheepLogger sheepLogger;
    public KitsFactory kitsFactory;
    public HashMap<String, JSONObject> playersData;

    public JsonFile configJson;
    public JsonFile playersJson;
    public JsonFile authJson;

    public SheepPvp() {
        prefix = ChatColor.RED + "Battle" + ChatColor.YELLOW + "Sheep";
        sheepLogger = new SheepLogger(this.prefix, this);
        kitsFactory = new KitsFactory(this);
    }

    @Override
    public void onEnable() {
        playersData = new HashMap<>();

        configJson = new JsonFile(new File(getDataFolder(), "config.json"), this);
        playersJson = new JsonFile(new File(getDataFolder(), "players.json"), this);
        authJson = new JsonFile(new File(getDataFolder(), "auth.json"), this);

        kitsFactory.factorate();
        getServer().getPluginManager().registerEvents(new ListenerManager(this, kitsFactory), this);

        ClearLagCommand.startClearLag(this);

        getCommand("say").setExecutor(new SayCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());

        getCommand("clearlag").setExecutor(new ClearLagCommand(this));
        getCommand("clearall").setExecutor(new ClearAllCommand());

        getCommand("login").setExecutor(new AuthentificationCommands(this));
        getCommand("register").setExecutor(new AuthentificationCommands(this));

        sheepLogger.info("Plugin enabled ! Version: " + configJson.getString("version"));
    }

    @Override
    public void onDisable() {
        sheepLogger.info("Plugin disabled !");
    }
}
