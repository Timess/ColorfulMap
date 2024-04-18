package cn.lunadeer.colorfulmap;

import cn.lunadeer.colorfulmap.generator.ImageRenderer;
import cn.lunadeer.colorfulmap.utils.Notification;
import cn.lunadeer.colorfulmap.utils.XLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.lunadeer.colorfulmap.Apis.getItemFrameMatrix;

public class Events implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void putImageMapsOnItemFrame(PlayerInteractEntityEvent event) {
        XLogger.debug("PlayerInteractEntityEvent called");
        // if not item frame return
        Entity entity = event.getRightClicked();
        if (!(entity instanceof ItemFrame)) {
            return;
        }
        ItemFrame item_frame = (ItemFrame) entity;
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        XLogger.debug("item: " + item);
        if (item.getType() == Material.AIR) {
            return;
        }
        // if not map return
        if (item.getType() != Material.FILLED_MAP) {
            return;
        }
        if (!item.getItemMeta().hasLore()) {
            return;
        }
        Player player = event.getPlayer();
        List<Component> lore = item.getItemMeta().lore();
        XLogger.debug("PlayerInteractEntityEvent");
        if (lore == null || lore.size() != 3) {
            return;
        }
        XLogger.debug("putImageMapsOnItemFrame");
        UUID uuid = UUID.fromString(((TextComponent) lore.get(0)).content());
        int count_x = Integer.parseInt(((TextComponent) lore.get(1)).content());
        int count_y = Integer.parseInt(((TextComponent) lore.get(2)).content());
        XLogger.debug("uuid: " + uuid);
        XLogger.debug("count_x: " + count_x);
        XLogger.debug("count_y: " + count_y);

        event.setCancelled(true);

        List<ItemFrame> item_frames = getItemFrameMatrix(player, item_frame, count_x, count_y);
        if (item_frames == null) {
            event.setCancelled(true);
            Notification.error(player, "没有足够的展示框阵列，尺寸应该为 " + count_x + "x" + count_y);
            return;
        }

        List<ItemStack> maps = new ArrayList<>();
        for (int y = 0; y < count_y; y++) {
            for (int x = 0; x < count_x; x++) {
                String path = "maps/" + uuid + "/" + x + "_" + y + ".png";
                ItemStack map = ImageRenderer.getMapItemFromImageTile(player, path);
                if (map == null) {
                    Notification.error(player, "无法加载地图，原始路径丢失：" + path);
                    return;
                }
                maps.add(map);
            }
        }

        XLogger.debug("maps size: " + maps.size());
        XLogger.debug("item_frames size: " + item_frames.size());
        for (int i = 0; i < item_frames.size(); i++) {
            item_frames.get(i).setItem(new ItemStack(Material.AIR));
            player.getInventory().setItemInMainHand(maps.get(i));
            PlayerInteractEntityEvent event_put_map = new PlayerInteractEntityEvent(player, item_frames.get(i));
            Bukkit.getPluginManager().callEvent(event_put_map);
            if (event_put_map.isCancelled()) {
                player.getInventory().setItemInMainHand(item);
                Notification.error(player, "无法放置地图");
                return;
            } else {
                item_frames.get(i).setItem(maps.get(i));
            }
        }
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
    }
}
