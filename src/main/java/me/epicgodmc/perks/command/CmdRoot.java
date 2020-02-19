package me.epicgodmc.perks.command;

import me.epicgodmc.perks.Perks;
import me.epicgodmc.perks.objects.SubCommand;
import me.epicgodmc.perks.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class CmdRoot implements CommandExecutor
{

    private ArrayList<SubCommand> commands = new ArrayList<>();
    private Perks plugin = Perks.getInstance();
    private MessageManager mm = plugin.mm;


    public CmdRoot()
    {

    }


    public String main = "perk";
    //SubCmds
    public String give = "give";
    public String logs = "logs";

    //

    public void setup(){
        plugin.getCommand(main).setExecutor(this);

        this.commands.add(new GiveSubCmd());
        this.commands.add(new LogSubCmd());


    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase(main))
        {
            if (sender.hasPermission("perks.admin")){
                if (args.length == 0) {
                    mm.getUsage().forEach((e) -> {
                        sender.sendMessage(mm.applyCC(e));
                    });
                    return true;
                }

                SubCommand target = this.get(args[0]);

                if (target == null) {
                    sender.sendMessage(mm.getMessage("cmdNotRecognized"));
                    return true;
                }

                ArrayList<String> argList = new ArrayList<>();
                argList.addAll(Arrays.asList(args));
                argList.remove(0);

                String[] arguments = argList.toArray(new String[argList.size()]);

                try {

                    target.onCommand(sender, arguments);
                } catch (Exception e) {
                    e.printStackTrace();
                    sender.sendMessage(mm.getMessage("error"));
                    return true;
                }
            }else{
                sender.sendMessage(mm.getMessage("noPermission"));
            }
        }
        return true;
    }


    private SubCommand get(String name) {
        Iterator<SubCommand> subcommands = this.commands.iterator();

        while (subcommands.hasNext()) {
            SubCommand sCmd = (SubCommand) subcommands.next();

            if (sCmd.name().equalsIgnoreCase(name)) {
                return sCmd;
            }

            String[] aliases;
            int length = (aliases = sCmd.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return sCmd;
                }
            }
        }
        return null;
    }

}
