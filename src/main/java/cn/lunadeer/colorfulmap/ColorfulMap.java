package cn.lunadeer.colorfulmap;

import cn.lunadeer.colorfulmap.commands.ToMap;
import cn.lunadeer.colorfulmap.utils.GiteaReleaseCheck;
import cn.lunadeer.colorfulmap.utils.XLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ColorfulMap extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        config = new Configuration(this);

        Objects.requireNonNull(Bukkit.getPluginCommand("tomap")).setExecutor(new ToMap());
        Bukkit.getPluginManager().registerEvents(new Events(), this);

        new MapManager().init();

        Metrics metrics = new Metrics(this, 21443);
        if (config.isCheckUpdate()) {
            giteaReleaseCheck = new GiteaReleaseCheck(this,
                    "https://ssl.lunadeer.cn:14446",
                    "zhangyuheng",
                    "ColorfulMap");
        }

        XLogger.info("ColorfulMap 已加载");
        XLogger.info("   _____      _             __       _ __  __");
        XLogger.info("  / ____|    | |           / _|     | |  \\/  |");
        XLogger.info(" | |     ___ | | ___  _ __| |_ _   _| | \\  / | __ _ _ __");
        XLogger.info(" | |    / _ \\| |/ _ \\| '__|  _| | | | | |\\/| |/ _` | '_ \\");
        XLogger.info(" | |___| (_) | | (_) | |  | | | |_| | | |  | | (_| | |_) |");
        XLogger.info("  \\_____\\___/|_|\\___/|_|  |_|  \\__,_|_|_|  |_|\\__,_| .__/");
        XLogger.info("                                                   | |");
        XLogger.info("                                                   |_|");
        XLogger.info("");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ColorfulMap instance;
    public static Configuration config;
    private GiteaReleaseCheck giteaReleaseCheck;
}
