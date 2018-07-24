/*
 * Copyright (C) 2018 eccentric_nz
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
package me.eccentric_nz.TARDIS.files;

import me.eccentric_nz.TARDIS.TARDIS;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * @author eccentric_nz
 */
public class TARDISSignsUpdater {

    private final TARDIS plugin;
    private final FileConfiguration signs_config;

    public TARDISSignsUpdater(TARDIS plugin, FileConfiguration signs_config) {
        this.plugin = plugin;
        this.signs_config = signs_config;
    }

    public void checkSignsConfig() {
        int i = 0;
        if (!signs_config.contains("junk")) {
            signs_config.set("junk", Collections.singletonList("Destination"));
            i++;
        }
        try {
            String signPath = plugin.getDataFolder() + File.separator + "language" + File.separator + "signs.yml";
            signs_config.save(new File(signPath));
            if (i > 0) {
                plugin.getConsole().sendMessage(plugin.getPluginName() + "Added " + ChatColor.AQUA + i + ChatColor.RESET + " new items to signs.yml");
            }
        } catch (IOException io) {
            plugin.debug("Could not save signs.yml, " + io.getMessage());
        }
    }
}