package me.epicgodmc.perks.utils;

import me.epicgodmc.perks.Perks;
import org.bukkit.ChatColor;

import java.util.List;

public class MessageManager {

    private Perks plugin = Perks.getInstance();

    public String applyCC(String input)
    {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public String prefix = plugin.config.getString("pluginPrefix");

    public List<String> getUsage()
    {
        return plugin.getConfig().getStringList("messages.usage");
    }


    public String getMessage(String input)
    {
        return applyCC(prefix+plugin.getConfig().getString("messages."+input));
    }

}
