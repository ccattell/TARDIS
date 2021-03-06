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
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Time travel is, as the name suggests, the (usually controlled) process of travelling through time, even in a
 * non-linear direction. In the 26th century individuals who time travel are sometimes known as persons of meta-temporal
 * displacement.
 *
 * @author eccentric_nz
 */
public class TARDISTemporalLocatorInventory {

    private final ItemStack[] temporal;
    private final TARDIS plugin;
    private final List<String> time = new ArrayList<>();

    public TARDISTemporalLocatorInventory(TARDIS plugin) {
        this.plugin = plugin;
        time.add(" ");
        time.add(" ");
        time.add(" ");
        time.add(" ");
        time.add("7 AM");
        time.add("8 AM");
        time.add("9 AM");
        time.add("10 AM");
        time.add("11 AM");
        time.add("12 AM");
        time.add("1 PM");
        time.add("2 PM");
        time.add("3 PM");
        time.add("4 PM");
        time.add("5 PM");
        time.add("6 PM");
        time.add("7 PM");
        time.add("8 PM");
        time.add("9 PM");
        time.add("10 PM");
        time.add("11 PM");
        time.add("12 PM");
        time.add("1 AM");
        time.add("2 AM");
        time.add("3 AM");
        time.add("4 AM");
        time.add("5 AM");
        temporal = getItemStack();
    }

    /**
     * Constructs an inventory for the Temporal Locator GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {
        ItemStack[] clocks = new ItemStack[27];
        // add morning
        ItemStack morn = new ItemStack(Material.CLOCK, 1);
        ItemMeta ing = morn.getItemMeta();
        ing.setDisplayName(plugin.getLanguage().getString("BUTTON_Morn"));
        ing.setLore(Arrays.asList("0 ticks", "6 AM"));
        morn.setItemMeta(ing);
        clocks[0] = morn;
        // add midday
        ItemStack mid = new ItemStack(Material.CLOCK, 1);
        ItemMeta day = mid.getItemMeta();
        day.setDisplayName(plugin.getLanguage().getString("BUTTON_NOON"));
        day.setLore(Arrays.asList("6000 ticks", "12 Noon"));
        mid.setItemMeta(day);
        clocks[1] = mid;
        // add night
        ItemStack nig = new ItemStack(Material.CLOCK, 1);
        ItemMeta ht = nig.getItemMeta();
        ht.setDisplayName(plugin.getLanguage().getString("BUTTON_NIGHT"));
        ht.setLore(Arrays.asList("12000 ticks", "6 PM"));
        nig.setItemMeta(ht);
        clocks[2] = nig;
        // add midnight
        ItemStack zero = new ItemStack(Material.CLOCK, 1);
        ItemMeta hrs = zero.getItemMeta();
        hrs.setDisplayName(plugin.getLanguage().getString("BUTTON_MID"));
        hrs.setLore(Arrays.asList("18000 ticks", "12 PM"));
        zero.setItemMeta(hrs);
        clocks[3] = zero;

        // add some clocks
        int c = 4;
        for (int i = 1000; i < 24000; i += 1000) {
            ItemStack is = new ItemStack(Material.CLOCK, 1);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(time.get(c));
            List<String> lore = new ArrayList<>();
            lore.add(i + " ticks");
            im.setLore(lore);
            is.setItemMeta(im);
            clocks[c] = is;
            c++;
        }
        return clocks;
    }

    public ItemStack[] getTemporal() {
        return temporal;
    }
}
