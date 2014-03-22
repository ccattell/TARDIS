/*
 * Copyright (C) 2014 eccentric_nz
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
package me.eccentric_nz.TARDIS.destroyers;

import java.util.Collections;
import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.builders.TARDISInteriorPostioning;
import me.eccentric_nz.TARDIS.builders.TARDISTIPSData;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.enumeration.SCHEMATIC;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;

/**
 * Destroys the inner TARDIS.
 *
 * If a TARDIS landed in the same space and time as another TARDIS, a time ram
 * could occur, destroying both TARDISes, their occupants and even cause a black
 * hole that would tear a hole in the universe
 *
 * @author eccentric_nz
 */
public class TARDISDestroyerInner {

    private final TARDIS plugin;

    public TARDISDestroyerInner(TARDIS plugin) {
        this.plugin = plugin;
    }

    /**
     * Destroys the inside of the TARDIS.
     *
     * @param schm the name of the schematic file to use can be DEFAULT, BIGGER
     * or DELUXE.
     * @param id the unique key of the record for this TARDIS in the database.
     * @param w the world where the TARDIS is to be built.
     * @param i the Material type id of the replacement block, this will either
     * be 0 (AIR) or 1 (STONE).
     * @param p the name of the player who owns the TARDIS.
     * @param slot the TIPS slot number
     */
    @SuppressWarnings("deprecation")
    public void destroyInner(SCHEMATIC schm, int id, World w, int i, String p, int slot) {
        // get dimensions
        short[] d;
        switch (schm) {
            case BIGGER:
                d = plugin.getBuildKeeper().getBiggerDimensions();
                break;
            case DELUXE:
                d = plugin.getBuildKeeper().getDeluxeDimensions();
                break;
            case ELEVENTH:
                d = plugin.getBuildKeeper().getEleventhDimensions();
                break;
            case REDSTONE:
                d = plugin.getBuildKeeper().getRedstoneDimensions();
                break;
            case STEAMPUNK:
                d = plugin.getBuildKeeper().getSteampunkDimensions();
                break;
            case PLANK:
                d = plugin.getBuildKeeper().getPlankDimensions();
                break;
            case TOM:
                d = plugin.getBuildKeeper().getTomDimensions();
                break;
            case ARS:
                d = plugin.getBuildKeeper().getARSDimensions();
                break;
            case CUSTOM:
                d = plugin.getBuildKeeper().getCustomDimensions();
                break;
            default:
                d = plugin.getBuildKeeper().getBudgetDimensions();
                break;
        }
        short h = d[0];
        short width = d[1];
        short l = d[2];
        // destroy TARDIS
        boolean below = (!plugin.getConfig().getBoolean("creation.create_worlds") && !plugin.getConfig().getBoolean("creation.default_world"));
        if (below) {
            int level, row, col, startx, startz, starty, resetx, resetz;
            // calculate startx, starty, startz
            int gsl[] = plugin.getUtils().getStartLocation(id);
            startx = gsl[0];
            resetx = gsl[1];
            starty = 14 + h;
            startz = gsl[2];
            resetz = gsl[3];
            for (level = 0; level < h; level++) {
                for (row = 0; row < width; row++) {
                    for (col = 0; col < l; col++) {
                        // set the block to stone / air
                        Block b = w.getBlockAt(startx, starty, startz);
                        Material m = b.getType();
                        // if it's a chest clear the inventory first
                        if (m.equals(Material.CHEST)) {
                            Chest container = (Chest) b.getState();
                            //Is it a double chest?
                            Chest chest = getDoubleChest(b);
                            if (chest != null) {
                                chest.getInventory().clear();
                                chest.getBlock().setTypeId(0);
                                container.getBlock().setTypeId(0);
                            } else if (container != null) {
                                container.getInventory().clear();
                                container.getBlock().setTypeId(0);
                            }
                        }
                        // if it's a furnace clear the inventory first
                        if (m.equals(Material.FURNACE)) {
                            Furnace fur = (Furnace) b.getState();
                            fur.getInventory().clear();
                        }
                        if (!m.equals(Material.CHEST)) {
                            if (w.getBlockAt(startx, starty, startz).getTypeId() != i) {
                                plugin.getUtils().setBlock(w, startx, starty, startz, i, (byte) 0);
                            }
                        }
                        startx += 1;
                    }
                    startx = resetx;
                    startz += 1;
                }
                startz = resetz;
                starty -= 1;
            }
        } else {
            TARDISInteriorPostioning tips = new TARDISInteriorPostioning(plugin);
            TARDISTIPSData coords = tips.getTIPSData(slot);
            tips.reclaimChunks(w, coords);
        }
        // remove blocks saved to blocks table (iron/gold/diamond/emerald)
        QueryFactory qf = new QueryFactory(plugin);
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("tardis_id", id);
        qf.doDelete("blocks", where);
        // remove from protectBlockMap - remove(Integer.valueOf(id)) would only remove the first one
        plugin.getGeneralKeeper().getProtectBlockMap().values().removeAll(Collections.singleton(Integer.valueOf(id)));
        if (plugin.isWorldGuardOnServer()) {
            plugin.getWorldGuardUtils().removeRegion(w, p);
        }
    }
    //Originally stolen from Babarix. Thank you :)

    @SuppressWarnings("deprecation")
    public Chest getDoubleChest(Block block) {
        Chest chest = null;
        if (block.getRelative(BlockFace.NORTH).getTypeId() == 54) {
            chest = (Chest) block.getRelative(BlockFace.NORTH).getState();
            return chest;
        } else if (block.getRelative(BlockFace.EAST).getTypeId() == 54) {
            chest = (Chest) block.getRelative(BlockFace.EAST).getState();
            return chest;
        } else if (block.getRelative(BlockFace.SOUTH).getTypeId() == 54) {
            chest = (Chest) block.getRelative(BlockFace.SOUTH).getState();
            return chest;
        } else if (block.getRelative(BlockFace.WEST).getTypeId() == 54) {
            chest = (Chest) block.getRelative(BlockFace.WEST).getState();
            return chest;
        }
        return chest;
    }
}