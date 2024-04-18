package cn.lunadeer.colorfulmap.generator;

import cn.lunadeer.colorfulmap.ColorfulMap;
import cn.lunadeer.colorfulmap.StorageMaps;
import cn.lunadeer.colorfulmap.utils.Notification;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static cn.lunadeer.colorfulmap.generator.TextRenderer.applyTextToMap;

public class Multi {

    public static ItemStack generate(Player player, String url, Float scale) {
        try {
            URL _url = new URL(url);
            BufferedImage image = ImageIO.read(_url);
            if (scale != 1.0) {
                int width = image.getWidth();
                int height = image.getHeight();
                int new_width = (int) (width * scale);
                int new_height = (int) (height * scale);
                BufferedImage newImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
                newImage.getGraphics().drawImage(image, 0, 0, new_width, new_height, null);
                image = newImage;
            }
            int image_width = image.getWidth();
            int image_height = image.getHeight();
            int x_count = (int) Math.ceil(image_width / 128.0);
            int y_count = (int) Math.ceil(image_height / 128.0);
            if (x_count > ColorfulMap.config.getMaxFrameX() || y_count > ColorfulMap.config.getMaxFrameY()) {
                Notification.error(player, "无法生成地图画: 图片太大，分辨率不得超过" + ColorfulMap.config.getMaxFrameX() * 128 + "x" + ColorfulMap.config.getMaxFrameY() * 128);
                return null;
            }
            int new_width = x_count * 128;
            int new_height = y_count * 128;
            BufferedImage newImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_ARGB);
            newImage.getGraphics().drawImage(image, (new_width - image_width) / 2, (new_height - image_height) / 2, null);
            image = newImage;
            image_width = image.getWidth();
            image_height = image.getHeight();
            List<BufferedImage> split_images = new ArrayList<>();
            for (int y = 0; y < y_count; y++) {
                for (int x = 0; x < x_count; x++) {
                    int width = Math.min(128, image_width - x * 128);
                    int height = Math.min(128, image_height - y * 128);
                    BufferedImage sub_image = image.getSubimage(x * 128, y * 128, width, height);
                    split_images.add(sub_image);
                }
            }
            if (split_images.size() == 0) {
                Notification.error(player, "无法生成地图画: 图片为空");
                return null;
            }
            UUID map_images_uuid = StorageMaps.save(player, split_images, x_count, y_count);
            if (map_images_uuid == null) {
                Notification.error(player, "无法生成地图画: 无法保存图片");
                return null;
            }
            List<String> map_info = new ArrayList<>();
            map_info.add("size: " + x_count + "x" + y_count);
            ItemStack mapItem = applyTextToMap(player, map_info);
            // add lore to map item (uuid, x_count, y_count)
            MapMeta meta = (MapMeta) mapItem.getItemMeta();
            List<Component> lore = new ArrayList<>();
            lore.add(Component.text(map_images_uuid.toString()));
            lore.add(Component.text(String.valueOf(x_count)));
            lore.add(Component.text(String.valueOf(y_count)));
            meta.lore(lore);
            mapItem.setItemMeta(meta);
            return mapItem;
        } catch (Exception e) {
            Notification.error(player, "无法生成地图画: " + e.getMessage());
            return null;
        }
    }
}
