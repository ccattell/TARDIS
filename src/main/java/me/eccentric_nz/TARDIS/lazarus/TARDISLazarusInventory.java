/*
 * Copyright (C) 2016 eccentric_nz
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

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import me.eccentric_nz.TARDIS.TARDIS;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * The Genetic Manipulation Device was invented by Professor Richard Lazarus.
 * The machine would turn anyone inside decades younger, but the process
 * contained one side effect: genes that evolution rejected and left dormant
 * would be unlocked, transforming the human into a giant skeletal scorpion-like
 * beast that fed off the lifeforce of living creatures.
 *
 * @author eccentric_nz
 */
public class TARDISLazarusInventory {

    private final ItemStack[] terminal;
    private final TARDIS plugin;
    LinkedHashMap<String, EntityType> disguises = new LinkedHashMap<String, EntityType>();

    public TARDISLazarusInventory(TARDIS plugin) {
        this.plugin = plugin;
        disguises.put("BAT", EntityType.BAT);
        disguises.put("BLAZE", EntityType.BLAZE);
        disguises.put("CAVE_SPIDER", EntityType.CAVE_SPIDER);
        disguises.put("CHICKEN", EntityType.CHICKEN);
        disguises.put("COW", EntityType.COW);
        disguises.put("CREEPER", EntityType.CREEPER);
        disguises.put("ENDERMAN", EntityType.ENDERMAN);
        disguises.put("ENDERMITE", EntityType.ENDERMITE);
        disguises.put("GUARDIAN", EntityType.GUARDIAN);
        disguises.put("ELDER_GUARDIAN", EntityType.ELDER_GUARDIAN);
        disguises.put("HORSE", EntityType.HORSE);
        disguises.put("DONKEY", EntityType.DONKEY);
        disguises.put("MULE", EntityType.MULE);
        disguises.put("SKELETON_HORSE", EntityType.SKELETON_HORSE);
        disguises.put("ZOMBIE_HORSE", EntityType.ZOMBIE_HORSE);
        disguises.put("LLAMA", EntityType.LLAMA);
        disguises.put("MAGMA_CUBE", EntityType.MAGMA_CUBE);
        disguises.put("MUSHROOM_COW", EntityType.MUSHROOM_COW);
        disguises.put("OCELOT", EntityType.OCELOT);
        disguises.put("PIG", EntityType.PIG);
        disguises.put("PIG_ZOMBIE", EntityType.PIG_ZOMBIE);
        disguises.put("POLAR_BEAR", EntityType.POLAR_BEAR);
        disguises.put("RABBIT", EntityType.RABBIT);
        disguises.put("SHEEP", EntityType.SHEEP);
        disguises.put("SILVERFISH", EntityType.SILVERFISH);
        disguises.put("SKELETON", EntityType.SKELETON);
        disguises.put("WITHER_SKELETON", EntityType.WITHER_SKELETON);
        disguises.put("STRAY", EntityType.STRAY);
        disguises.put("SLIME", EntityType.SLIME);
        disguises.put("SPIDER", EntityType.SPIDER);
        disguises.put("SQUID", EntityType.SQUID);
        disguises.put("VILLAGER", EntityType.VILLAGER);
        disguises.put("WITCH", EntityType.WITCH);
        disguises.put("WOLF", EntityType.WOLF);
        disguises.put("ZOMBIE", EntityType.ZOMBIE);
        disguises.put("ZOMBIE_VILLAGER", EntityType.ZOMBIE_VILLAGER);
        disguises.put("HUSK", EntityType.HUSK);
        disguises.put("EVOKER", EntityType.EVOKER);
        disguises.put("VEX", EntityType.VEX);
        disguises.put("VINDICATOR", EntityType.VINDICATOR);
        this.terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Temporal Locator GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {
        ItemStack[] eggs = new ItemStack[54];
        int i = 0;
        for (Map.Entry<String, EntityType> map : disguises.entrySet()) {
            ItemStack egg = plugin.getTardisHelper().setSpawnEggType(new ItemStack(Material.MONSTER_EGG, 1), map.getValue());
            ItemMeta ime = egg.getItemMeta();
            ime.setDisplayName(map.getKey());
            egg.setItemMeta(ime);
            eggs[i] = egg;
            i++;
        }
        // put iron golem
        ItemStack iron = new ItemStack(Material.IRON_BLOCK, 1);
        ItemMeta golem = iron.getItemMeta();
        golem.setDisplayName("IRON_GOLEM");
        iron.setItemMeta(golem);
        eggs[i] = iron;
        i++;
        // put snowman
        ItemStack snow = new ItemStack(Material.SNOW_BALL, 1);
        ItemMeta man = snow.getItemMeta();
        man.setDisplayName("SNOWMAN");
        snow.setItemMeta(man);
        eggs[i] = snow;
        i++;
        // put wither
        ItemStack wit = new ItemStack(Material.SKULL_ITEM, 1, (byte) 1);
        ItemMeta her = wit.getItemMeta();
        her.setDisplayName("WITHER");
        wit.setItemMeta(her);
        eggs[i] = wit;
        // add options
        ItemStack the = new ItemStack(Material.REDSTONE_COMPARATOR, 1);
        ItemMeta master = the.getItemMeta();
        master.setDisplayName(plugin.getLanguage().getString("BUTTON_MASTER"));
        master.setLore(Arrays.asList(plugin.getLanguage().getString("SET_OFF")));
        the.setItemMeta(master);
        eggs[45] = the;
        ItemStack adult = new ItemStack(Material.HOPPER, 1);
        ItemMeta baby = adult.getItemMeta();
        baby.setDisplayName(plugin.getLanguage().getString("BUTTON_AGE"));
        baby.setLore(Arrays.asList("ADULT"));
        adult.setItemMeta(baby);
        eggs[47] = adult;
        ItemStack typ = new ItemStack(Material.INK_SACK, 1, (byte) 6);
        ItemMeta col = typ.getItemMeta();
        col.setDisplayName(plugin.getLanguage().getString("BUTTON_TYPE"));
        col.setLore(Arrays.asList("WHITE"));
        typ.setItemMeta(col);
        eggs[48] = typ;
        ItemStack tamed = new ItemStack(Material.LEASH, 1);
        ItemMeta tf = tamed.getItemMeta();
        tf.setDisplayName(plugin.getLanguage().getString("BUTTON_OPTS"));
        tf.setLore(Arrays.asList("FALSE"));
        tamed.setItemMeta(tf);
        eggs[49] = tamed;
        // add buttons
        ItemStack rem = new ItemStack(Material.APPLE, 1);
        ItemMeta ove = rem.getItemMeta();
        ove.setDisplayName(plugin.getLanguage().getString("BUTTON_RESTORE"));
        rem.setItemMeta(ove);
        eggs[51] = rem;
        // set
        ItemStack s = new ItemStack(Material.BOOK_AND_QUILL, 1);
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
