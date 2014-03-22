/*
 * Copyright (C) 2014 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.travel;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetCurrentLocation;
import me.eccentric_nz.TARDIS.enumeration.COMPASS;
import me.eccentric_nz.TARDIS.enumeration.MESSAGE;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 *
 * @author eccentric_nz
 */
public class TARDISCaveFinder {

    private final TARDIS plugin;

    public TARDISCaveFinder(TARDIS plugin) {
        this.plugin = plugin;
    }

    public Location searchCave(Player p, int id) {
        Location l = null;
        // get the current TARDIS location
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("tardis_id", id);
        ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, where);
        if (rsc.resultSet()) {
            World w = rsc.getWorld();
            int startx = rsc.getX();
            int startz = rsc.getZ();
            COMPASS d = rsc.getDirection();
            // Assume all non-nether/non-end world environments are NORMAL
            if (!w.getEnvironment().equals(World.Environment.NETHER) && !w.getEnvironment().equals(World.Environment.THE_END)) {
                if (!w.getWorldType().equals(WorldType.FLAT) && worldCheck(w)) {
                    int limitx = 2000;
                    int limitz = 2000;
                    int step = 25;
                    // search in a random direction
                    Integer[] directions = new Integer[]{0, 1, 2, 3};
                    Collections.shuffle(Arrays.asList(directions));
                    for (int i = 0; i < 4; i++) {
                        switch (directions[i]) {
                            case 0:
                                // east
                                TARDISMessage.send(p, plugin.getPluginName() + "Looking east...");
                                for (int east = startx; east < east + limitx; east += step) {
                                    Check chk = isThereRoom(w, east, startz, d);
                                    if (chk.isSafe()) {
                                        TARDISMessage.send(p, plugin.getPluginName() + "Cave found in an easterly direction!");
                                        return new Location(w, east, chk.getY(), startz);
                                    }
                                }
                                break;
                            case 1:
                                // south
                                TARDISMessage.send(p, plugin.getPluginName() + "Looking south...");
                                for (int south = startz; south < south + limitz; south += step) {
                                    Check chk = isThereRoom(w, startx, south, d);
                                    if (chk.isSafe()) {
                                        TARDISMessage.send(p, plugin.getPluginName() + "Cave found in a southerly direction!");
                                        return new Location(w, startx, chk.getY(), south);
                                    }
                                }
                                break;
                            case 2:
                                // west
                                TARDISMessage.send(p, plugin.getPluginName() + "Looking west...");
                                for (int west = startx; west > west - limitx; west -= step) {
                                    Check chk = isThereRoom(w, west, startz, d);
                                    if (chk.isSafe()) {
                                        TARDISMessage.send(p, plugin.getPluginName() + "Cave found in a westerly direction!");
                                        return new Location(w, west, chk.getY(), startz);
                                    }
                                }
                                break;
                            case 3:
                                // north
                                TARDISMessage.send(p, plugin.getPluginName() + "Looking north...");
                                for (int north = startz; north > north - limitz; north -= step) {
                                    Check chk = isThereRoom(w, startx, north, d);
                                    if (chk.isSafe()) {
                                        TARDISMessage.send(p, plugin.getPluginName() + "Cave found in a northerly direction!");
                                        return new Location(w, startx, chk.getY(), north);
                                    }
                                }
                                break;
                        }
                    }
                }
            } else {
                TARDISMessage.send(p, plugin.getPluginName() + "You cannot travel to a cave in the " + w.getEnvironment().toString() + "!");
            }
        } else {
            TARDISMessage.send(p, plugin.getPluginName() + MESSAGE.NO_CURRENT.getText());
        }
        return l;
    }

    private Check isThereRoom(World w, int x, int z, COMPASS d) {
        Check ret = new Check();
        ret.setSafe(false);
        for (int y = 35; y > 14; y--) {
            if (w.getBlockAt(x, y, z).getType().equals(Material.AIR)) {
                int yy = getLowestAirBlock(w, x, y, z);
                // check there is enough height for the police box
                if (yy <= y - 3 && w.getBlockAt(x - 1, yy - 1, z - 1).getType().equals(Material.STONE)) {
                    // check there is room for the police box
                    if (w.getBlockAt(x - 1, yy, z - 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x - 1, yy, z).getType().equals(Material.AIR)
                            && w.getBlockAt(x - 1, yy, z + 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x, yy, z - 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x, yy, z + 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x + 1, yy, z - 1).getType().equals(Material.AIR)
                            && w.getBlockAt(x + 1, yy, z).getType().equals(Material.AIR)
                            && w.getBlockAt(x + 1, yy, z + 1).getType().equals(Material.AIR)) {
                        // finally check there is space to exit the police box
                        boolean safe = false;
                        switch (d) {
                            case NORTH:
                                if (w.getBlockAt(x - 1, yy, z + 2).getType().equals(Material.AIR)
                                        && w.getBlockAt(x, yy, z + 2).getType().equals(Material.AIR)
                                        && w.getBlockAt(x + 1, yy, z + 2).getType().equals(Material.AIR)) {
                                    safe = true;
                                }
                                break;
                            case WEST:
                                if (w.getBlockAt(x + 2, yy, z - 1).getType().equals(Material.AIR)
                                        && w.getBlockAt(x + 2, yy, z).getType().equals(Material.AIR)
                                        && w.getBlockAt(x + 2, yy, z + 1).getType().equals(Material.AIR)) {
                                    safe = true;
                                }
                                break;
                            case SOUTH:
                                if (w.getBlockAt(x - 1, yy, z - 2).getType().equals(Material.AIR)
                                        && w.getBlockAt(x, yy, z - 2).getType().equals(Material.AIR)
                                        && w.getBlockAt(x + 1, yy, z - 2).getType().equals(Material.AIR)) {
                                    safe = true;
                                }
                                break;
                            default:
                                if (w.getBlockAt(x - 2, yy, z - 1).getType().equals(Material.AIR)
                                        && w.getBlockAt(x - 2, yy, z).getType().equals(Material.AIR)
                                        && w.getBlockAt(x - 2, yy, z + 1).getType().equals(Material.AIR)) {
                                    safe = true;
                                }
                                break;
                        }
                        if (safe) {
                            ret.setSafe(true);
                            ret.setY(yy);
                        }
                    }
                }
            }
        }
        return ret;
    }

    private int getLowestAirBlock(World w, int x, int y, int z) {
        int yy = y;
        while (w.getBlockAt(x, yy, z).getRelative(BlockFace.DOWN).getType().equals(Material.AIR)) {
            yy--;
        }
        return yy;
    }

    private boolean worldCheck(World w) {
        Location spawn = w.getSpawnLocation();
        int y = w.getHighestBlockYAt(spawn);
        if (y < 15) {
            return false;
        } else {
            // move 20 blocks north
            spawn.setZ(spawn.getBlockZ() - 20);
            int ny = w.getHighestBlockYAt(spawn);
            spawn.setX(spawn.getBlockZ() + 20);
            int ey = w.getHighestBlockYAt(spawn);
            spawn.setZ(spawn.getBlockZ() + 20);
            int sy = w.getHighestBlockYAt(spawn);
            return (y != ny && y != ey && y != sy);
        }
    }

    private class Check {

        private boolean safe;
        private int y;

        public boolean isSafe() {
            return safe;
        }

        public void setSafe(boolean safe) {
            this.safe = safe;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}