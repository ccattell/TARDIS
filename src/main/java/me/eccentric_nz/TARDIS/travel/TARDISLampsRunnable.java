/*
 * Copyright (C) 2019 eccentric_nz
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

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.List;

/**
 * Phosphor lamps are used for lighting. They use electron excitation; when shaken, they grow brighter.
 *
 * @author eccentric_nz
 */
class TARDISLampsRunnable implements Runnable {

    private final TARDIS plugin;
    private final List<Block> lamps;
    private final long end;
    private final BlockData light;
    private final BlockData BLACK = TARDISConstants.BLACK;
    private final BlockData SPONGE = Material.SPONGE.createBlockData();
    private final boolean use_wool;
    private final boolean lights_on;
    private int task;
    private Location handbrake_loc;

    TARDISLampsRunnable(TARDIS plugin, List<Block> lamps, long end, Material light, boolean use_wool) {
        this.plugin = plugin;
        this.lamps = lamps;
        this.end = end;
        this.light = light.createBlockData();
        this.use_wool = use_wool;
        lights_on = (lamps.get(0).getType().equals(this.light.getMaterial()));
    }

    @Override
    public void run() {
        if (System.currentTimeMillis() > end) {
            // set all lamps back to whatever they were when the malfunction happened
            if (lights_on) {
                lamps.forEach((b) -> {
                    if (b.getType().equals(Material.SPONGE) || b.getType().equals(Material.BLACK_WOOL)) {
                        b.setBlockData(light);
                    }
                });
            } else {
                lamps.forEach((b) -> {
                    if (b.getType().equals(light.getMaterial())) {
                        if (use_wool) {
                            b.setBlockData(BLACK);
                        } else {
                            b.setBlockData(SPONGE);
                        }
                    }
                });
            }
            plugin.getServer().getScheduler().cancelTask(task);
        } else {
            // play smoke effect
            for (int j = 0; j < 9; j++) {
                handbrake_loc.getWorld().playEffect(handbrake_loc, Effect.SMOKE, j);
            }
            lamps.forEach((b) -> {
                if (b.getType().equals(light.getMaterial())) {
                    if (use_wool) {
                        b.setBlockData(BLACK);
                    } else {
                        b.setBlockData(SPONGE);
                    }
                } else if (b.getType().equals(Material.SPONGE) || b.getType().equals(Material.BLACK_WOOL)) {
                    b.setBlockData(light);
                }
            });
        }
    }

    public void setTask(int task) {
        this.task = task;
    }

    public void setHandbrake(Location handbrake_loc) {
        this.handbrake_loc = handbrake_loc;
    }
}
