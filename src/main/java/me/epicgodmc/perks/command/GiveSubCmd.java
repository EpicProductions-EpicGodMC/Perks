package me.epicgodmc.perks.command;

import me.epicgodmc.perks.Perks;
import me.epicgodmc.perks.objects.SubCommand;
import me.epicgodmc.perks.utils.ItemBuilder;
import me.epicgodmc.perks.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GiveSubCmd extends SubCommand {

    private Perks plugin = Perks.getInstance();
    private MessageManager mm = plugin.mm;

    private FileConfiguration perkConfig = plugin.fileManager.getPerkConfig();

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null)
        {
            if (isLoaded(args[1]))
            {
                target.getInventory().addItem(getPerk(args[1]));
            }else{
                sender.sendMessage(mm.getMessage("typeNotFound"));
            }
        }else{
            sender.sendMessage(mm.getMessage("playerNotFound"));
        }
    }

    public boolean isLoaded(String type)
    {
        return perkConfig.isSet("perks."+type);
    }

    public ItemStack getPerk(String type)
    {
        String display = mm.applyCC(perkConfig.getString("perks."+type+".displayName"));
        Material mat = Material.valueOf(perkConfig.getString("perks."+type+".itemType").toUpperCase());
        int damageValue = perkConfig.getInt("perks."+type+".damage");
        boolean glow = perkConfig.getBoolean("perks."+type+".glow");
        List<String> lore = new ArrayList<>();
        perkConfig.getStringList("perks."+type+".lore").forEach((e) -> {
            lore.add(mm.applyCC(e));
        });



        return new ItemBuilder(mat,1, (byte) damageValue).setName(display).setLore(lore).setGlow(glow).addNbtString("PERK", type).toItemStack();
    }


    @Override
    public String name() {
        return plugin.getInstance().cmdRoot.give;
    }

    @Override
    public String info() {
        return "";
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
