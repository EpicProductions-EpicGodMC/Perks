package me.epicgodmc.perks;

import me.epicgodmc.perks.Listeners.RedeemListener;
import me.epicgodmc.perks.command.CmdRoot;
import me.epicgodmc.perks.utils.FileManager;
import me.epicgodmc.perks.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Perks extends JavaPlugin {

    private static Perks instance;
    public FileConfiguration config = this.getConfig();

    public MessageManager mm;
    public CmdRoot cmdRoot;
    public FileManager fileManager;



    public static Perks getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;


        registerInstances();
        setupFiles();
        registerEvents();

        cmdRoot.setup();

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"[Perks] version 1.0.0 has been enabled");

    }

    @Override
    public void onDisable() {
        instance = null;

    }

    public void setupFiles()
    {
        saveDefaultConfig();
    }

    public void registerEvents()
    {
        this.getServer().getPluginManager().registerEvents(new RedeemListener(), this);
    }

    public void registerInstances()
    {
        mm = new MessageManager();
        cmdRoot = new CmdRoot();
        fileManager = new FileManager(this);

    }
}
