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
package me.eccentric_nz.TARDIS.flight;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.api.event.TARDISDematerialisationEvent;
import me.eccentric_nz.TARDIS.database.*;
import me.eccentric_nz.TARDIS.database.data.Tardis;
import me.eccentric_nz.TARDIS.destroyers.DestroyData;
import me.eccentric_nz.TARDIS.enumeration.COMPASS;
import me.eccentric_nz.TARDIS.enumeration.PRESET;
import me.eccentric_nz.TARDIS.utility.TARDISMessage;
import me.eccentric_nz.TARDIS.utility.TARDISSounds;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author eccentric_nz
 */
class TARDISDematerialiseToVortex implements Runnable {

    private final TARDIS plugin;
    private final int id;
    private final Player player;
    private final Location handbrake;

    TARDISDematerialiseToVortex(TARDIS plugin, int id, Player player, Location handbrake) {
        this.plugin = plugin;
        this.id = id;
        this.player = player;
        this.handbrake = handbrake;
    }

    @Override
    public void run() {
        UUID uuid = player.getUniqueId();
        plugin.getTrackerKeeper().getInVortex().add(id);
        plugin.getTrackerKeeper().getDidDematToVortex().add(id);
        HashMap<String, Object> wherei = new HashMap<>();
        wherei.put("tardis_id", id);
        ResultSetTardis rs = new ResultSetTardis(plugin, wherei, "", false, 2);
        if (rs.resultSet()) {
            Tardis tardis = rs.getTardis();
            boolean hidden = tardis.isHidden();
            HashMap<String, Object> wherecl = new HashMap<>();
            wherecl.put("tardis_id", id);
            ResultSetCurrentLocation rscl = new ResultSetCurrentLocation(plugin, wherecl);
            String resetw = "";
            Location l = null;
            QueryFactory qf = new QueryFactory(plugin);
            if (!rscl.resultSet()) {
                hidden = true;
            } else {
                resetw = rscl.getWorld().getName();
                l = new Location(rscl.getWorld(), rscl.getX(), rscl.getY(), rscl.getZ());
                // set back to current location
                HashMap<String, Object> bid = new HashMap<>();
                bid.put("tardis_id", id);
                HashMap<String, Object> bset = new HashMap<>();
                bset.put("world", rscl.getWorld().getName());
                bset.put("x", rscl.getX());
                bset.put("y", rscl.getY());
                bset.put("z", rscl.getZ());
                bset.put("direction", rscl.getDirection().toString());
                bset.put("submarine", rscl.isSubmarine());
                qf.doUpdate("back", bset, bid);
            }
            COMPASS cd = rscl.getDirection();
            boolean sub = rscl.isSubmarine();
            Biome biome = rscl.getBiome();
            DestroyData dd = new DestroyData(plugin, uuid.toString());
            dd.setDirection(cd);
            dd.setLocation(l);
            dd.setPlayer(player);
            dd.setHide(false);
            dd.setOutside(false);
            dd.setSubmarine(sub);
            dd.setTardisID(id);
            dd.setBiome(biome);
            PRESET preset = tardis.getPreset();
            if (preset.equals(PRESET.JUNK_MODE)) {
                HashMap<String, Object> wherenl = new HashMap<>();
                wherenl.put("tardis_id", id);
                ResultSetNextLocation rsn = new ResultSetNextLocation(plugin, wherenl);
                if (!rsn.resultSet()) {
                    TARDISMessage.send(player, "DEST_NO_LOAD");
                    return;
                }
                Location exit = new Location(rsn.getWorld(), rsn.getX(), rsn.getY(), rsn.getZ());
                dd.setFromToLocation(exit);
            }
            plugin.getPM().callEvent(new TARDISDematerialisationEvent(player, tardis, l));
            if (!hidden && !plugin.getTrackerKeeper().getReset().contains(resetw)) {
                HashMap<String, Object> wherek = new HashMap<>();
                wherek.put("uuid", uuid.toString());
                ResultSetPlayerPrefs rsp = new ResultSetPlayerPrefs(plugin, wherek);
                boolean minecart = (rsp.resultSet()) && rsp.isMinecartOn();
                // play demat sfx
                if (!minecart) {
                    if (!preset.equals(PRESET.JUNK_MODE)) {
                        String sound = (plugin.getTrackerKeeper().getMalfunction().get(id) && plugin.getTrackerKeeper().getHasDestination().containsKey(id)) ? "tardis_malfunction_takeoff" : "tardis_takeoff";
                        TARDISSounds.playTARDISSound(handbrake, sound);
                        TARDISSounds.playTARDISSound(l, sound);
                    } else {
                        TARDISSounds.playTARDISSound(handbrake, "junk_takeoff");
                    }
                } else {
                    handbrake.getWorld().playSound(handbrake, Sound.ENTITY_MINECART_INSIDE, 1.0F, 0.0F);
                }
                plugin.getTrackerKeeper().getDematerialising().add(id);
                plugin.getPresetDestroyer().destroyPreset(dd);
            } else {
                // set hidden false!
                HashMap<String, Object> set = new HashMap<>();
                set.put("hidden", 0);
                HashMap<String, Object> whereh = new HashMap<>();
                whereh.put("tardis_id", id);
                qf.doUpdate("tardis", set, whereh);
                plugin.getPresetDestroyer().removeBlockProtection(id, qf);
                // restore biome
                plugin.getUtils().restoreBiome(l, biome);
            }
        }
    }
}
