package me.epicgodmc.perks.utils;

import me.epicgodmc.perks.Perks;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private Perks plugin;

    public FileManager(Perks plugin) {
        this.plugin = plugin;

        createFiles();
    }


    ////////////
    private File perkFile;
    private FileConfiguration perkConfig;
    //
    private File logFile;
    private FileConfiguration logConfig;

    ////////////


    private void createFiles() {
        logFile = new File(plugin.getDataFolder(), "Logs.yml");
        perkFile = new File(plugin.getDataFolder(), "Perks.yml");

        if (!logFile.exists())
        {
            logFile.getParentFile().mkdirs();
            plugin.saveResource("Logs.yml", false);
        }
        logConfig = new YamlConfiguration();

        if (!perkFile.exists()) {
            perkFile.getParentFile().mkdirs();
            plugin.saveResource("Perks.yml", false);
        }

        perkConfig = new YamlConfiguration();

        try {

            logConfig.load(logFile);
            perkConfig.load(perkFile);

        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("[Perks] Failed to create files!!");
            e.printStackTrace();
        }

    }

    public FileConfiguration getLogConfig() {
        return this.logConfig;
    }

    public void saveLogConfig()
    {
        try{
            logConfig.save(logFile);
        }catch (IOException e)
        {
            Bukkit.getConsoleSender().sendMessage("[Perks] Failed to save Logs.yml !!");
        }
    }


    public FileConfiguration getPerkConfig() {
        return this.perkConfig;
    }

    public void savePerkConfig() {
        try {
            perkConfig.save(perkFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[Perks] Failed to save Perks.yml !!");
            e.printStackTrace();
        }
    }


}
