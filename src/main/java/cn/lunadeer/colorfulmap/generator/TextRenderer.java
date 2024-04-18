package cn.lunadeer.colorfulmap.generator;

import cn.lunadeer.colorfulmap.utils.Notification;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TextRenderer extends MapRenderer {

    public TextRenderer(List<String> text) {
        this.text = text;
    }

    private final List<String> text;

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
            int y = 0;
            for (String line : text) {
                canvas.drawText(0, y, MinecraftFont.Font, line);
                y += 10;
            }
        } catch (Exception e) {
            Notification.error(player, "Failed to render text: " + e.getMessage());
        }
    }

    public static ItemStack applyTextToMap(Player player, List<String> text){
        ItemStack mapItem = new ItemStack(Material.FILLED_MAP, 1);
        MapMeta meta = (MapMeta) mapItem.getItemMeta();
        MapView mapView = Bukkit.createMap(player.getWorld());
        TextRenderer renderer = new TextRenderer(text);
        mapView.addRenderer(renderer);
        meta.setMapView(mapView);
        mapItem.setItemMeta(meta);
        return mapItem;
    }
}
