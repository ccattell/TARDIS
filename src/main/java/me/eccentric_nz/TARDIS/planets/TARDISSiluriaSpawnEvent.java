/*
 * Copyright (C) 2017 eccentric_nz
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
package me.eccentric_nz.TARDIS.planets;

import java.util.Random;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.tardisweepingangels.TARDISWeepingAngelsAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 *
 * @author eccentric_nz
 */
public final class TARDISSiluriaSpawnEvent implements Listener {

    private final TARDIS plugin;
    private final TARDISWeepingAngelsAPI twaAPI;
    private final Random r = new Random();

    public TARDISSiluriaSpawnEvent(TARDIS plugin, TARDISWeepingAngelsAPI twaAPI) {
        this.plugin = plugin;
        this.twaAPI = TARDISAngelsAPI.getAPI(this.plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDalekSpawn(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.SPAWNER)) {
            return;
        }
        if (!event.getLocation().getWorld().getName().equals("Siluria")) {
            return;
        }
        if (!event.getEntity().getType().equals(EntityType.SKELETON)) {
            return;
        }
        final LivingEntity le = event.getEntity();
        // it's a Dalek - disguise it!
        twaAPI.setSilurianEquipment(le, false);
    }
}
