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
package me.eccentric_nz.TARDIS.move;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetCurrentLocation;
import me.eccentric_nz.TARDIS.database.ResultSetDoorBlocks;
import me.eccentric_nz.TARDIS.database.ResultSetTardis;
import me.eccentric_nz.TARDIS.enumeration.PRESET;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.Openable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
public class TARDISDoorCloser {

    private final TARDIS plugin;
    private final UUID uuid;
    private final int id;

    public TARDISDoorCloser(TARDIS plugin, UUID uuid, int id) {
        this.plugin = plugin;
        this.uuid = uuid;
        this.id = id;
    }

    public void closeDoors() {
        // get door locations
        ResultSetDoorBlocks rs = new ResultSetDoorBlocks(plugin, id);
        if (rs.resultSet()) {
            close(rs.getOuterBlock(), rs.getInnerBlock().getLocation());
            // inner
            if (!rs.getInnerBlock().getChunk().isLoaded()) {
                rs.getInnerBlock().getChunk().load();
            }
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> close(rs.getInnerBlock(), null), 5L);
        }
    }

    /**
     * Close the door.
     *
     * @param block    the bottom door block
     * @param inportal the location of the interior door portal
     */
    private void close(Block block, Location inportal) {
        if (block != null && plugin.getGeneralKeeper().getDoors().contains(block.getType())) {
            Openable closeable = (Openable) block.getBlockData();
            closeable.setOpen(false);
            block.setBlockData(closeable, true);
        }
        if (inportal != null && plugin.getConfig().getBoolean("preferences.walk_in_tardis")) {
            // get all companion UUIDs
            List<UUID> uuids = new ArrayList<>();
            HashMap<String, Object> where = new HashMap<>();
            where.put("tardis_id", id);
            ResultSetTardis rs = new ResultSetTardis(plugin, where, "", false, 2);
            if (rs.resultSet()) {
                if (!plugin.getConfig().getBoolean("preferences.open_door_policy")) {
                    String[] companions = rs.getTardis().getCompanions().split(":");
                    for (String c : companions) {
                        if (!c.isEmpty()) {
                            uuids.add(UUID.fromString(c));
                        }
                    }
                    uuids.add(uuid);
                }
            }
            // get locations
            // exterior portal (from current location)
            HashMap<String, Object> where_exportal = new HashMap<>();
            where_exportal.put("tardis_id", id);
            ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(plugin, where_exportal);
            rsc.resultSet();
            Location exportal = new Location(rsc.getWorld(), rsc.getX(), rsc.getY(), rsc.getZ());
            if (rs.getTardis().getPreset().equals(PRESET.SWAMP)) {
                exportal.add(0.0d, 1.0d, 0.0d);
            }
            // unset trackers
            if (!plugin.getConfig().getBoolean("preferences.open_door_policy")) {
                // players
                uuids.forEach((u) -> plugin.getTrackerKeeper().getMover().remove(u));
            }
            // locations
            plugin.getTrackerKeeper().getPortals().remove(exportal);
            plugin.getTrackerKeeper().getPortals().remove(inportal);
        }
    }
}
