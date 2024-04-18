package cn.lunadeer.colorfulmap;

import cn.lunadeer.colorfulmap.generator.ImageRenderer;
import cn.lunadeer.colorfulmap.utils.XLogger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MapManager implements Listener {

    public static MapManager instance = null;

    private final MapsFile dataFile = new MapsFile();
    /*
    world_name:
        id: "path/to/image.png"
     */
    private final Map<String, Map<Integer, BufferedImage>> savedImages = new HashMap<>();

    /***
     * Call this method in the onEnable()
     * Code:
     * ImageManager manager = ImageManger.getInstance();
     * manager.init();
     */
    public void init() {
        MapManager.instance = this;
        Bukkit.getPluginManager().registerEvents(this, ColorfulMap.instance);
        loadImages();
    }


    @EventHandler
    public void onMapInitEvent(MapInitializeEvent event) {
        World world = event.getMap().getWorld();
        if (world == null) {
            return;
        }
        int id = event.getMap().getId();
        if (!hasImage(world, id)) {
            return;
        }
        MapView view = event.getMap();
        view.getRenderers().clear();
        BufferedImage image = getImage(world, id);
        if (image == null) {
            XLogger.warn("图片丢失，ID：" + id);
            return;
        }
        view.addRenderer(new ImageRenderer(image));
    }

    /***
     * Whenever a new map is created, save the ID and Image to data file.
     *
     * @param id - MapView ID
     * @param path - Image Path
     */
    public void saveImage(World world, Integer id, String path) {
        String world_name = world.getName();
        FileConfiguration config = dataFile.getConfig();
        config.set(world_name + "." + id, path);
        dataFile.saveConfig();
        if (!savedImages.containsKey(world_name)) {
            savedImages.put(world_name, new HashMap<>());
        }
        BufferedImage image = StorageMaps.load(path);
        savedImages.get(world_name).put(id, image);
    }


    /***
     * Loads images from data file to HashMap.
     */
    private void loadImages() {
        FileConfiguration config = dataFile.getConfig();
        for (String world : config.getKeys(false)) {
            for (String id : Objects.requireNonNull(config.getConfigurationSection(world)).getKeys(false)) {
                String path = config.getString(world + "." + id);
                if (path == null) {
                    continue;
                }
                BufferedImage image = StorageMaps.load(path);
                if (image == null) {
                    XLogger.err("无法加载图片: " + path);
                    continue;
                }
                if (!savedImages.containsKey(world)) {
                    savedImages.put(world, new HashMap<>());
                }
                savedImages.get(world).put(Integer.parseInt(id), image);
            }
        }
    }


    public boolean hasImage(World world_name, int id) {
        return savedImages.containsKey(world_name.getName()) && savedImages.get(world_name.getName()).containsKey(id);
    }


    public BufferedImage getImage(World world_name, int id) {
        return savedImages.get(world_name.getName()).get(id);
    }


    static class MapsFile {

        private final ColorfulMap plugin = ColorfulMap.instance;
        private FileConfiguration dataConfig = null;
        private File dataConfigFile = null;
        private final String path = "maps/maps.yml";

        public MapsFile() {
            saveDefaultConfig();
        }

        public void reloadConfig() {
            if (dataConfigFile == null)
                dataConfigFile = new File(plugin.getDataFolder(), path);

            this.dataConfig = YamlConfiguration
                    .loadConfiguration(dataConfigFile);

            InputStream defConfigStream = plugin.getResource(path);
            if (defConfigStream != null) {
                YamlConfiguration defConfig = YamlConfiguration
                        .loadConfiguration(new InputStreamReader(defConfigStream));
                this.dataConfig.setDefaults(defConfig);
            }
        }

        public FileConfiguration getConfig() {
            if (this.dataConfig == null)
                reloadConfig();
            return this.dataConfig;
        }

        public void saveConfig() {
            if ((dataConfig == null) || (dataConfigFile == null))
                return;
            try {
                getConfig().save(dataConfigFile);
            } catch (IOException e) {
                XLogger.err("Could not save config to " + dataConfigFile);
            }
        }

        public void saveDefaultConfig() {
            if (dataConfigFile == null)
                dataConfigFile = new File(plugin.getDataFolder(), path);
            if (!dataConfigFile.exists())
                plugin.saveResource(path, false);
        }

    }
}
