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
package me.eccentric_nz.TARDIS.lazarus;

import me.eccentric_nz.TARDIS.TARDIS;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Genetic Manipulation Device was invented by Professor Richard Lazarus. The machine would turn anyone inside
 * decades younger, but the process contained one side effect: genes that evolution rejected and left dormant would be
 * unlocked, transforming the human into a giant skeletal scorpion-like beast that fed off the lifeforce of living
 * creatures.
 *
 * @author eccentric_nz
 */
class TARDISLazarusExtraInventory {

    private final ItemStack[] terminal;
    private final TARDIS plugin;
    private final List<Material> disguises = new ArrayList<>();

    TARDISLazarusExtraInventory(TARDIS plugin) {
        this.plugin = plugin;
        disguises.add(Material.COD_SPAWN_EGG);
        disguises.add(Material.PUFFERFISH_SPAWN_EGG);
        disguises.add(Material.SALMON_SPAWN_EGG);
        disguises.add(Material.TROPICAL_FISH_SPAWN_EGG);
        disguises.add(Material.GHAST_SPAWN_EGG);
        disguises.add(Material.PHANTOM_SPAWN_EGG);
        disguises.add(Material.VINDICATOR_SPAWN_EGG);
        disguises.add(Material.PILLAGER_SPAWN_EGG);
        disguises.add(Material.RAVAGER_SPAWN_EGG);
        disguises.add(Material.TRADER_LLAMA_SPAWN_EGG);
        disguises.add(Material.WANDERING_TRADER_SPAWN_EGG);
        disguises.add(Material.ZOMBIE_HORSE_SPAWN_EGG);
        disguises.add(Material.SKELETON_HORSE_SPAWN_EGG);
        terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Temporal Locator GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {
        ItemStack[] eggs = new ItemStack[54];
        int i = 0;
        // golems
        // put iron golem
        ItemStack iron = new ItemStack(Material.IRON_BLOCK, 1);
        ItemMeta golem = iron.getItemMeta();
        golem.setDisplayName("IRON_GOLEM");
        iron.setItemMeta(golem);
        eggs[i] = iron;
        i++;
        // put snowman
        ItemStack snow = new ItemStack(Material.SNOWBALL, 1);
        ItemMeta man = snow.getItemMeta();
        man.setDisplayName("SNOWMAN");
        snow.setItemMeta(man);
        eggs[i] = snow;
        i++;
        // put wither
        ItemStack wit = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
        ItemMeta her = wit.getItemMeta();
        her.setDisplayName("WITHER");
        wit.setItemMeta(her);
        eggs[i] = wit;
        i++;
        // put illusioner
        ItemStack ill = new ItemStack(Material.BOW, 1);
        ItemMeta usi = ill.getItemMeta();
        usi.setDisplayName("ILLUSIONER");
        ill.setItemMeta(usi);
        eggs[i] = ill;
        i++;
        // fish / ghast / phantom / illagers / dead horses / traders
        for (Material m : disguises) {
            ItemStack egg = new ItemStack(m, 1);
            ItemMeta me = egg.getItemMeta();
            me.setDisplayName(m.toString().replace("_SPAWN_EGG", ""));
            egg.setItemMeta(me);
            eggs[i] = egg;
            i++;
        }
        // if TARDISWeepingAngels is enabled angels, cybermen and ice warriors will be available
        if (plugin.checkTWA()) {
            ItemStack weep = new ItemStack(Material.BRICK, 1);
            ItemMeta ing = weep.getItemMeta();
            ing.setDisplayName("WEEPING ANGEL");
            weep.setItemMeta(ing);
            eggs[i] = weep;
            i++;
            ItemStack cyber = new ItemStack(Material.IRON_INGOT, 1);
            ItemMeta men = cyber.getItemMeta();
            men.setDisplayName("CYBERMAN");
            cyber.setItemMeta(men);
            eggs[i] = cyber;
            i++;
            ItemStack ice = new ItemStack(Material.SNOWBALL, 1);
            ItemMeta war = ice.getItemMeta();
            war.setDisplayName("ICE WARRIOR");
            ice.setItemMeta(war);
            eggs[i] = ice;
            i++;
            ItemStack emp = new ItemStack(Material.SUGAR, 1);
            ItemMeta tyc = emp.getItemMeta();
            tyc.setDisplayName("EMPTY CHILD");
            emp.setItemMeta(tyc);
            eggs[i] = emp;
            i++;
            ItemStack sil = new ItemStack(Material.FEATHER, 1);
            ItemMeta uri = sil.getItemMeta();
            uri.setDisplayName("SILURIAN");
            sil.setItemMeta(uri);
            eggs[i] = sil;
            i++;
            ItemStack son = new ItemStack(Material.POTATO, 1);
            ItemMeta tar = son.getItemMeta();
            tar.setDisplayName("SONTARAN");
            son.setItemMeta(tar);
            eggs[i] = son;
            i++;
            ItemStack str = new ItemStack(Material.BAKED_POTATO, 1);
            ItemMeta axs = str.getItemMeta();
            axs.setDisplayName("STRAX");
            str.setItemMeta(axs);
            eggs[i] = str;
            i++;
            ItemStack vas = new ItemStack(Material.BOOK, 1);
            ItemMeta hta = vas.getItemMeta();
            hta.setDisplayName("VASHTA NERADA");
            vas.setItemMeta(hta);
            eggs[i] = vas;
            i++;
            ItemStack zyg = new ItemStack(Material.PAINTING, 1);
            ItemMeta onz = zyg.getItemMeta();
            onz.setDisplayName("ZYGON");
            zyg.setItemMeta(onz);
            eggs[i] = zyg;
        }
        // add options
        ItemStack the = new ItemStack(Material.COMPARATOR, 1);
        ItemMeta master = the.getItemMeta();
        master.setDisplayName(plugin.getLanguage().getString("BUTTON_MASTER"));
        master.setLore(Collections.singletonList(plugin.getLanguage().getString("SET_OFF")));
        the.setItemMeta(master);
        eggs[45] = the;
        ItemStack adult = new ItemStack(Material.HOPPER, 1);
        ItemMeta baby = adult.getItemMeta();
        baby.setDisplayName(plugin.getLanguage().getString("BUTTON_AGE"));
        baby.setLore(Collections.singletonList("ADULT"));
        adult.setItemMeta(baby);
        eggs[47] = adult;
        ItemStack typ = new ItemStack(Material.CYAN_DYE, 1);
        ItemMeta col = typ.getItemMeta();
        col.setDisplayName(plugin.getLanguage().getString("BUTTON_TYPE"));
        col.setLore(Collections.singletonList("WHITE"));
        typ.setItemMeta(col);
        eggs[48] = typ;
        ItemStack tamed = new ItemStack(Material.LEAD, 1);
        ItemMeta tf = tamed.getItemMeta();
        tf.setDisplayName(plugin.getLanguage().getString("BUTTON_OPTS"));
        tf.setLore(Collections.singletonList("FALSE"));
        tamed.setItemMeta(tf);
        eggs[49] = tamed;
        // add buttons
        ItemStack rem = new ItemStack(Material.APPLE, 1);
        ItemMeta ove = rem.getItemMeta();
        ove.setDisplayName(plugin.getLanguage().getString("BUTTON_RESTORE"));
        rem.setItemMeta(ove);
        eggs[51] = rem;
        // set
        ItemStack s = new ItemStack(Material.WRITABLE_BOOK, 1);
        ItemMeta sim = s.getItemMeta();
        sim.setDisplayName(plugin.getLanguage().getString("BUTTON_DNA"));
        s.setItemMeta(sim);
        eggs[52] = s;
        ItemStack can = new ItemStack(Material.BOWL, 1);
        ItemMeta cel = can.getItemMeta();
        cel.setDisplayName(plugin.getLanguage().getString("BUTTON_CANCEL"));
        can.setItemMeta(cel);
        eggs[53] = can;

        return eggs;
    }

    public ItemStack[] getTerminal() {
        return terminal;
    }
}
