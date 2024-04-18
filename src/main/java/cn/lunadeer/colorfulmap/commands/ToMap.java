package cn.lunadeer.colorfulmap.commands;

import cn.lunadeer.colorfulmap.ColorfulMap;
import cn.lunadeer.colorfulmap.generator.Multi;
import cn.lunadeer.colorfulmap.utils.Notification;
import cn.lunadeer.colorfulmap.utils.Time;
import cn.lunadeer.colorfulmap.utils.XLogger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ToMap implements CommandExecutor {
    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            XLogger.warn("只有玩家可以使用此命令");
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            Notification.error(player, "用法 /tomap <图片url> [缩放比例(选填，默认1)]");
            return true;
        }
        String url = args[0];
        float scale = 1;
        if (args.length == 2) {
            try {
                scale = Float.parseFloat(args[1]);
            } catch (NumberFormatException e) {
                Notification.error(player, "缩放比例必须是数字");
                return true;
            }
        }
//        ItemStack mapImage = Multi.generate(player, url, scale);
//        if (mapImage == null){
//            Notification.error(player, "生成地图失败");
//            return true;
//        }
//        player.getInventory().addItem(mapImage);
        float finalScale = scale;
        Time.runAsync(ColorfulMap.instance, () -> {
                    ItemStack mapImage = Multi.generate(player, url, finalScale);
                    if (mapImage == null) {
                        Notification.error(player, "生成地图失败");
                        return;
                    }
                    player.getInventory().addItem(mapImage);
                }
        );
        return true;
    }
}
