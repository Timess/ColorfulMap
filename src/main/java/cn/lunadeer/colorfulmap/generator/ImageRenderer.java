package cn.lunadeer.colorfulmap.generator;

import cn.lunadeer.colorfulmap.MapManager;
import cn.lunadeer.colorfulmap.StorageMaps;
import cn.lunadeer.colorfulmap.utils.Notification;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class ImageRenderer extends MapRenderer {
    public ImageRenderer(BufferedImage image) {
        this.image = image;
    }

    private final BufferedImage image;

    /**
     * Render to the given map.
     *
     * @param map    The MapView being rendered to.
     * @param canvas The canvas to use for rendering.
     * @param player The player who triggered the rendering.
     */
    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        try {
            if (image.getWidth() > 128 || image.getHeight() > 128) {
                Notification.error(player, "Failed to render image: image is too large");
                return;
            }
            int x_offset = (128 - image.getWidth()) / 2;
            int y_offset = (128 - image.getHeight()) / 2;
            canvas.drawImage(x_offset, y_offset, image);
        } catch (Exception e) {
            Notification.error(player, "Failed to render image: " + e.getMessage());
        }
    }

    public static ItemStack getMapItemFromImageTile(Player player, String path){
        BufferedImage image = StorageMaps.load(path);
        if (image == null) {
            Notification.error(player, "图片丢失，无法加载，请重新生成。");
            return null;
        }
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta meta = (MapMeta) mapItem.getItemMeta();
        MapView mapView = Bukkit.createMap(player.getWorld());
        ImageRenderer renderer = new ImageRenderer(image);
        mapView.getRenderers().clear();
        mapView.addRenderer(renderer);
        meta.setMapView(mapView);
        mapItem.setItemMeta(meta);
        MapManager.instance.saveImage(player.getWorld(), mapView.getId(), path);
        return mapItem;
    }
}
