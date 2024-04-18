package cn.lunadeer.colorfulmap;

import cn.lunadeer.colorfulmap.utils.Notification;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Apis {

    public static List<ItemFrame> getItemFrameMatrix(Player player, ItemFrame left_bottom, Integer x, Integer y) {
        List<ItemFrame> item_frames = new ArrayList<>();
        Location corner = left_bottom.getLocation();
        BlockFace facing = left_bottom.getFacing();
        if (facing == BlockFace.UP || facing == BlockFace.DOWN) {
            Notification.warn(player, "暂时不支持上下方向的展示框阵列");
            return null;
            /*
            // └
            if (getItemFrame(new Location(corner.getWorld(), corner.getBlockX() + x - 1, corner.getBlockY(), corner.getBlockZ() - y + 1)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX() + j, corner.getBlockY(), corner.getBlockZ() - i));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
            // ┘
            if (getItemFrame(new Location(corner.getWorld(), corner.getBlockX() - y + 1, corner.getBlockY(), corner.getBlockZ() - x + 1)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX() - j, corner.getBlockY(), corner.getBlockZ() - i));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
            // ┐
            if (getItemFrame(new Location(corner.getWorld(), corner.getBlockX() - x + 1, corner.getBlockY(), corner.getBlockZ() + y - 1)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX() - j, corner.getBlockY(), corner.getBlockZ() + i));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
            // ┌
            if (getItemFrame(new Location(corner.getWorld(), corner.getBlockX() + y - 1, corner.getBlockY(), corner.getBlockZ() + x - 1)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX() + j, corner.getBlockY(), corner.getBlockZ() + i));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
            */

        }
        if (facing == BlockFace.NORTH) {
            int t_r_x = corner.getBlockX() - x + 1;
            int t_r_y = corner.getBlockY() + y - 1;
            int t_r_z = corner.getBlockZ();
            if (getItemFrame(new Location(corner.getWorld(), t_r_x, t_r_y, t_r_z)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX() - i, corner.getBlockY() + j, corner.getBlockZ()));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
        }
        if (facing == BlockFace.SOUTH) {
            int t_r_x = corner.getBlockX() + x - 1;
            int t_r_y = corner.getBlockY() + y - 1;
            int t_r_z = corner.getBlockZ();
            if (getItemFrame(new Location(corner.getWorld(), t_r_x, t_r_y, t_r_z)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX() + i, corner.getBlockY() + j, corner.getBlockZ()));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
        }
        if (facing == BlockFace.WEST) {
            int t_r_x = corner.getBlockX();
            int t_r_y = corner.getBlockY() + y - 1;
            int t_r_z = corner.getBlockZ() + x - 1;
            if (getItemFrame(new Location(corner.getWorld(), t_r_x, t_r_y, t_r_z)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX(), corner.getBlockY() + j, corner.getBlockZ() + i));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
        }
        if (facing == BlockFace.EAST) {
            int t_r_x = corner.getBlockX();
            int t_r_y = corner.getBlockY() + y - 1;
            int t_r_z = corner.getBlockZ() - x + 1;
            if (getItemFrame(new Location(corner.getWorld(), t_r_x, t_r_y, t_r_z)) != null) {
                for (int j = y - 1; j >= 0; j--) {
                    for (int i = 0; i < x; i++) {
                        ItemFrame item_frame = getItemFrame(new Location(corner.getWorld(), corner.getBlockX(), corner.getBlockY() + j, corner.getBlockZ() - i));
                        if (item_frame == null) {
                            Notification.error(player, "展示框阵列不完整");
                            return null;
                        }
                        item_frames.add(item_frame);
                    }
                }
                return item_frames;
            }
        }
        return null;
    }

    public static ItemFrame getItemFrame(Location loc) {
        Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
        for (Entity entity : entities) {
            if (entity.getLocation().getBlockX() != loc.getBlockX() || entity.getLocation().getBlockY() != loc.getBlockY() || entity.getLocation().getBlockZ() != loc.getBlockZ()) {
                continue;
            }
            if (entity instanceof ItemFrame) {
                return (ItemFrame) entity;
            }
        }
        return null;
    }

}
