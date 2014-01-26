/*
 * Copyright (C) 2013 eccentric_nz
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
package me.eccentric_nz.TARDIS.commands;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

/**
 * TabCompleter for /tardisrecipe command
 */
public class TARDISRecipeTabComplete implements TabCompleter {

    private final List<String> ROOT_SUBS = ImmutableList.of("a-circuit", "ars-circuit", "bio-circuit", "biome-disk", "blank", "c-circuit", "cell", "d-circuit", "e-circuit", "filter", "i-circuit", "key", "l-circuit", "locator", "m-circuit", "mem-circuit", "oscillator", "player-disk", "preset-disk", "p-circuit", "r-circuit", "remote", "s-circuit", "save-disk", "scan-circuit", "sonic", "t-circuit");

    public TARDISRecipeTabComplete() {
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length <= 1) {
            return partial(args[0], ROOT_SUBS);
        }
        return ImmutableList.of();
    }

    private List<String> partial(String token, Collection<String> from) {
        return StringUtil.copyPartialMatches(token, from, new ArrayList<String>(from.size()));
    }
}
