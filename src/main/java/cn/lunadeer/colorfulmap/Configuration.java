package cn.lunadeer.colorfulmap;

import org.bukkit.configuration.file.FileConfiguration;

public class Configuration {

    public Configuration(ColorfulMap plugin) {
        _plugin = plugin;
        _plugin.saveDefaultConfig();
        reload();
        _plugin.saveConfig();
    }

    public void reload() {
        _plugin.reloadConfig();
        _file = _plugin.getConfig();
        _debug = _file.getBoolean("Debug", false);
        _max_frame_x = _file.getInt("MaxFrameX", 32);
        _max_frame_y = _file.getInt("MaxFrameY", 18);
        _check_update = _file.getBoolean("CheckUpdate", true);
    }

    public Boolean isDebug() {
        return _debug;
    }

    public void setDebug(Boolean debug) {
        _debug = debug;
        _file.set("Debug", debug);
        _plugin.saveConfig();
    }

    public Integer getMaxFrameX() {
        return _max_frame_x;
    }

    public void setMaxFrameX(Integer max_frame_x) {
        _max_frame_x = max_frame_x;
        _file.set("MaxFrameX", max_frame_x);
        _plugin.saveConfig();
    }

    public Integer getMaxFrameY() {
        return _max_frame_y;
    }

    public void setMaxFrameY(Integer max_frame_y) {
        _max_frame_y = max_frame_y;
        _file.set("MaxFrameY", max_frame_y);
        _plugin.saveConfig();
    }

    public Boolean isCheckUpdate() {
        return _check_update;
    }

    private final ColorfulMap _plugin;
    private FileConfiguration _file;
    private Boolean _debug;
    private Integer _max_frame_x;
    private Integer _max_frame_y;
    private Boolean _check_update;
}
