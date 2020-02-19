package me.epicgodmc.perks.Listeners;

import de.tr7zw.nbtapi.NBTItem;
import me.epicgodmc.perks.Perks;
import me.epicgodmc.perks.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

public class RedeemListener implements Listener {

    private Perks plugin = Perks.getInstance();
    private MessageManager mm = plugin.mm;
    private FileConfiguration perkConfig = plugin.fileManager.getPerkConfig();
    private FileConfiguration logs = plugin.fileManager.getLogConfig();


    @EventHandler
    public void redeem(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            String value = getPerk(e.getPlayer().getInventory().getItemInMainHand());
            if (!value.equals("false")) {
                if (perkConfig.getString("perks."+value+".deny-node").equalsIgnoreCase("null") || !e.getPlayer().hasPermission(perkConfig.getString("perks." + value + ".deny-node"))) {


                    decrementItemInHandBy(1, e.getPlayer());
                    runCommands(value, e.getPlayer().getName());

                    String success = perkConfig.getString("perks."+value+".messages.success");
                    e.getPlayer().sendMessage(mm.applyCC(success.replace("%PLAYER%", e.getPlayer().getName()).replace("%player%", e.getPlayer().getName())));

                    saveLog(e.getPlayer(), value, getDate());
                }else{
                    String denied = perkConfig.getString("perks."+value+".messages.denied");
                    e.getPlayer().sendMessage(mm.applyCC(denied.replace("%PLAYER%", e.getPlayer().getName()).replace("%player%", e.getPlayer().getName())));
                }
            }
        }
    }

    public void decrementItemInHandBy(int amt, Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        int current = item.getAmount();

        if (current == 1) {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            return;
        }
        item.setAmount(current - amt);
        player.getInventory().setItemInMainHand(item);

    }

    private void saveLog(Player player, String type, String date) {
        String user = player.getUniqueId().toString();

        List<String> confList = logs.getStringList(user);

        confList.add(date + ";" + type);
        logs.set(user, confList);

        plugin.fileManager.saveLogConfig();

    }

    private String getDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yy:MM:dd:HH:mm");
        fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        return fmt.format(timestamp);
    }

    private void runCommands(String type, String playerName) {
        for (String cmd : perkConfig.getStringList("perks." + type + ".commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formatCmd(cmd, playerName));
        }


    }

    private String formatCmd(String cmd, String playerName) {
        return cmd.replace("%PLAYER%", playerName).replace("%player%", playerName);
    }

    private String getPerk(ItemStack i) {
        if (i != null && !i.getType().equals(Material.AIR)) {
            NBTItem nbtItem = new NBTItem(i);
            if (nbtItem.hasKey("PERK")) return nbtItem.getString("PERK");
        } else return "false";
        return "false";
    }


}
