package me.epicgodmc.perks.command;

import me.epicgodmc.perks.Perks;
import me.epicgodmc.perks.objects.SubCommand;
import me.epicgodmc.perks.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class LogSubCmd extends SubCommand {

    private Perks plugin = Perks.getInstance();
    private MessageManager mm = plugin.mm;

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target != null)
        {
            sendLogs(target.getUniqueId(), sender);


        }else{
            sender.sendMessage(mm.getMessage("playerNotFound"));
        }


    }

// MM:dd:HH:mm:ss
    public void sendLogs(UUID target, CommandSender requester)
    {
        List<String> logs = plugin.fileManager.getLogConfig().getStringList(target.toString());

        requester.sendMessage(mm.applyCC("&5&lRecorded Logs:\n&5&l========================="));
        for (String str : logs)
        {
            String[] dataString = str.split(";");

            String[] dateData = dataString[0].split(":");
            String type = dataString[1];

            requester.sendMessage(mm.applyCC(String.format("&cRedeemed &e&l%s &cat &e&l%s/%s/%s %s:%s", type, dateData[2], dateData[1], "20"+dateData[0], dateData[3], dateData[4])));
        }
        requester.sendMessage(mm.applyCC("&5&l========================="));


    }

    @Override
    public String name() {
        return plugin.cmdRoot.logs;
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
