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
import me.eccentric_nz.TARDIS.database.ResultSetDoors;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISNumberParsers;
import me.eccentric_nz.TARDIS.utility.TARDISSounds;
import me.eccentric_nz.TARDIS.utility.TARDISStaticLocationGetters;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Emergency Programme One was a feature of the Doctor's TARDIS designed to return a companion to a designated place in
 * case of extreme emergency.
 *
 * @author eccentric_nz
 */
public class TARDISEPSRunnable implements Runnable {

    private final TARDIS plugin;
    private final String message;
    private final Player tl;
    private final List<UUID> players;
    private final int id;
    private final String eps;
    private final String creeper;

    public TARDISEPSRunnable(TARDIS plugin, String message, Player tl, List<UUID> players, int id, String eps, String creeper) {
        this.plugin = plugin;
        this.message = message;
        this.tl = tl;
        this.players = players;
        this.id = id;
        this.eps = eps;
        this.creeper = creeper;
    }

    @Override
    public void run() {
        Location l = getSpawnLocation(id);
        if (l != null) {
            try {
                TARDISSounds.playTARDISSound(l, "tardis_takeoff");
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> TARDISSounds.playTARDISSound(l, "tardis_land"), 490L);
                plugin.setTardisSpawn(true);
                l.setX(l.getX() + 0.5F);
                l.setZ(l.getZ() + 1.5F);
                // set yaw if npc spawn location has been changed
                if (!eps.isEmpty()) {
                    String[] creep = creeper.split(":");
                    double cx = TARDISNumberParsers.parseDouble(creep[1]);
                    double cz = TARDISNumberParsers.parseDouble(creep[3]);
                    float yaw = getCorrectYaw(cx, cz, l.getX(), l.getZ());
                    l.setYaw(yaw);
                }
                // create NPC
                NPCRegistry registry = CitizensAPI.getNPCRegistry();
                NPC npc = registry.createNPC(EntityType.PLAYER, tl.getName());
                npc.spawn(l);
                int npcid = npc.getId();
                if (npc.isSpawned()) {
                    // set the lookclose trait
                    plugin.getServer().dispatchCommand(plugin.getConsole(), "npc select " + npcid);
                    plugin.getServer().dispatchCommand(plugin.getConsole(), "npc lookclose");
                }
                plugin.getGeneralKeeper().getNpcIDs().add(npcid);
                players.forEach((p) -> {
                    Player pp = plugin.getServer().getPlayer(p);
                    if (pp != null) {
                        TARDISMessage.message(pp, ChatColor.RED + "[Emergency Programme One] " + ChatColor.RESET + message);
                        TARDISMessage.message(pp, ChatColor.RED + "[Emergency Programme One] " + ChatColor.RESET + plugin.getLanguage().getString("EP1_INFO"));
                    }
                });
            } catch (CommandException e) {
                plugin.debug(e.getMessage());
            }
        }
    }

    /**
     * @param id the TARDIS to look up
     * @return the EP1 spawn location
     */
    private Location getSpawnLocation(int id) {
        if (!eps.isEmpty()) {
            return TARDISStaticLocationGetters.getSpawnLocationFromDB(eps);
        } else if (plugin.getConfig().getBoolean("creation.create_worlds")) {
            // get world spawn location
            return plugin.getServer().getWorld("TARDIS_WORLD_" + tl.getName()).getSpawnLocation();
        } else {
            HashMap<String, Object> where = new HashMap<>();
            where.put("tardis_id", id);
            where.put("door_type", 1);
            ResultSetDoors rsd = new ResultSetDoors(plugin, where, false);
            if (rsd.resultSet()) {
                int x = 0;
                int z = 0;
                Location location = TARDISStaticLocationGetters.getSpawnLocationFromDB(rsd.getDoor_location());
                switch (rsd.getDoor_direction()) {
                    case NORTH:
                        z = -2;
                        break;
                    case EAST:
                        x = 1;
                        z = -1;
                        break;
                    case WEST:
                        x = -1;
                        z = -1;
                        break;
                    default:
                        break;
                }
                return location.add(x, 0, z);
            } else {
                return null;
            }
        }
    }

    /**
     * Determines the angle of a straight line drawn between point one and two. The number returned, which is a double
     * in degrees, tells us how much we have to rotate a horizontal line clockwise for it to match the line between the
     * two points.
     *
     * @param px1 the x coordinate of the first point
     * @param pz1 the z coordinate of the first point
     * @param px2 the x coordinate of the second point
     * @param pz2 the z coordinate of the second point
     * @return the head angle of EP1
     */
    private static float getCorrectYaw(double px1, double pz1, double px2, double pz2) {
        double xDiff = px2 - px1;
        double zDiff = pz2 - pz1;
        return (float) Math.toDegrees(Math.atan2(zDiff, xDiff)) + 90F;
    }
}
