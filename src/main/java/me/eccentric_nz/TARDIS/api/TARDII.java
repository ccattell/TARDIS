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
package me.eccentric_nz.TARDIS.api;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISTrackerInstanceKeeper;
import me.eccentric_nz.TARDIS.builders.BuildData;
import me.eccentric_nz.TARDIS.builders.TARDISAbandoned;
import me.eccentric_nz.TARDIS.database.*;
import me.eccentric_nz.TARDIS.database.data.Tardis;
import me.eccentric_nz.TARDIS.desktop.TARDISUpgradeData;
import me.eccentric_nz.TARDIS.desktop.TARDISWallFloorRunnable;
import me.eccentric_nz.TARDIS.enumeration.*;
import me.eccentric_nz.TARDIS.flight.TARDISTakeoff;
import me.eccentric_nz.TARDIS.move.TARDISTeleportLocation;
import me.eccentric_nz.TARDIS.rooms.TARDISWalls;
import me.eccentric_nz.TARDIS.travel.TARDISPluginRespect;
import me.eccentric_nz.TARDIS.utility.TARDISLocationGetters;
import me.eccentric_nz.TARDIS.utility.TARDISUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;

/**
 * @author eccentric_nz
 */
public class TARDII implements TardisAPI {

    private final TARDISDatabaseConnection service = TARDISDatabaseConnection.getINSTANCE();
    private final Connection connection = service.getConnection();
    private final Random random = new Random();

    @Override
    public HashMap<String, Integer> getTimelordMap() {
        HashMap<String, Integer> timelords = new HashMap<>();
        Statement statement = null;
        ResultSet rs = null;
        String query = "SELECT tardis_id, owner FROM " + TARDIS.plugin.getPrefix() + "tardis";
        try {
            service.testConnection(connection);
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    timelords.put(rs.getString("owner"), rs.getInt("tardis_id"));
                }
            }
        } catch (SQLException e) {
            TARDIS.plugin.debug("ResultSet error for tardis table! " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                TARDIS.plugin.debug("Error closing tardis table! " + e.getMessage());
            }
        }
        return timelords;
    }

    @Override
    public Location getTARDISCurrentLocation(int id) {
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        ResultSetCurrentLocation rs = new ResultSetCurrentLocation(TARDIS.plugin, where);
        if (rs.resultSet()) {
            return new Location(rs.getWorld(), rs.getX(), rs.getY(), rs.getZ());
        }
        return null;
    }

    @Override
    public Location getTARDISCurrentLocation(Player p) {
        return getTARDISCurrentLocation(p.getUniqueId());
    }

    @Override
    public Location getTARDISCurrentLocation(UUID uuid) {
        HashMap<String, Object> where = new HashMap<>();
        where.put("uuid", uuid.toString());
        ResultSetCurrentLocation rs = new ResultSetCurrentLocation(TARDIS.plugin, where);
        if (rs.resultSet()) {
            return new Location(rs.getWorld(), rs.getX(), rs.getY(), rs.getZ());
        }
        return null;
    }

    @Override
    public Location getTARDISNextLocation(int id) {
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        ResultSetNextLocation rs = new ResultSetNextLocation(TARDIS.plugin, where);
        if (rs.resultSet()) {
            return new Location(rs.getWorld(), rs.getX(), rs.getY(), rs.getZ());
        }
        return null;
    }

    @Override
    public TARDISData getTARDISMapData(int id) {
        TARDISData data = null;
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        ResultSetTardis rs = new ResultSetTardis(TARDIS.plugin, where, "", false, 2);
        if (rs.resultSet()) {
            Tardis tardis = rs.getTardis();
            Location current = getTARDISCurrentLocation(id);
            String console = tardis.getSchematic().getPermission().toUpperCase(Locale.ENGLISH);
            String chameleon = tardis.getPreset().toString();
            String door = "Closed";
            for (Map.Entry<Location, TARDISTeleportLocation> map : TARDIS.plugin.getTrackerKeeper().getPortals().entrySet()) {
                if (!map.getKey().getWorld().getName().contains("TARDIS") && !map.getValue().isAbandoned()) {
                    if (id == map.getValue().getTardisId()) {
                        door = "Open";
                        break;
                    }
                }
            }
            String powered = (tardis.isPowered_on()) ? "Yes" : "No";
            String siege = (tardis.isSiege_on()) ? "Yes" : "No";
            String abandoned = (tardis.isAbandoned()) ? "Yes" : "No";
            List<String> occupants = getPlayersInTARDIS(id);
            data = new TARDISData(current, console, chameleon, door, powered, siege, abandoned, occupants);
        }
        return data;
    }

    @Override
    public Location getRandomLocation(List<String> worlds, Environment environment, Parameters param) {
        if (environment == null) {
            // choose random environment
            environment = Environment.values()[random.nextInt(Environment.values().length)];
        }
        switch (environment) {
            case NETHER:
                return new TARDISRandomNether(TARDIS.plugin, worlds, param).getlocation();
            case THE_END:
                return new TARDISRandomTheEnd(TARDIS.plugin, worlds, param).getlocation();
            default:
                return new TARDISRandomOverworld(TARDIS.plugin, worlds, param).getlocation();
        }
    }

    @Override
    public Location getRandomLocation(List<String> worlds, Environment environment, Player p) {
        return getRandomLocation(getWorlds(), null, new Parameters(p, FLAG.getAPIFlags()));
    }

    @Override
    public Location getRandomLocation(List<String> worlds, Player p) {
        return getRandomLocation(getWorlds(), null, new Parameters(p, FLAG.getAPIFlags()));
    }

    @Override
    public Location getRandomOverworldLocation(Player p) {
        return getRandomLocation(getWorlds(), Environment.NORMAL, p);
    }

    @Override
    public Location getRandomOverworldLocation(String world, Player p) {
        return getRandomLocation(Collections.singletonList(world), Environment.NORMAL, p);
    }

    @Override
    public Location getRandomNetherLocation(Player p) {
        return getRandomLocation(getWorlds(), Environment.NETHER, p);
    }

    @Override
    public Location getRandomNetherLocation(String world, Player p) {
        return getRandomLocation(Collections.singletonList(world), Environment.NETHER, p);
    }

    @Override
    public Location getRandomEndLocation(Player p) {
        return getRandomLocation(getWorlds(), Environment.THE_END, p);
    }

    @Override
    public Location getRandomEndLocation(String world, Player p) {
        return getRandomLocation(Collections.singletonList(world), Environment.THE_END, p);
    }

    @Override
    public List<String> getWorlds() {
        List<String> worlds = new ArrayList<>();
        Bukkit.getWorlds().forEach((w) -> {
            String name = w.getName();
            if (TARDIS.plugin.getPlanetsConfig().getBoolean("planets." + name + ".time_travel")) {
                if (TARDIS.plugin.getWorldManager().equals(WORLD_MANAGER.MULTIVERSE)) {
                    worlds.add(TARDIS.plugin.getMVHelper().getAlias(name));
                } else {
                    worlds.add(name);
                }
            }
        });
        return worlds;
    }

    @Override
    public List<String> getOverWorlds() {
        List<String> worlds = new ArrayList<>();
        Bukkit.getWorlds().forEach((w) -> {
            String name = w.getName();
            if (TARDIS.plugin.getPlanetsConfig().getBoolean("planets." + name + ".time_travel") && !w.getEnvironment().equals(Environment.NETHER) && !w.getEnvironment().equals(Environment.THE_END)) {
                if (TARDIS.plugin.getWorldManager().equals(WORLD_MANAGER.MULTIVERSE)) {
                    worlds.add(TARDIS.plugin.getMVHelper().getAlias(name));
                } else {
                    worlds.add(name);
                }
            }
        });
        return worlds;
    }

    @Override
    public String getTARDISPlayerIsIn(Player p) {
        return getTARDISPlayerIsIn(p.getUniqueId());
    }

    @Override
    public String getTARDISPlayerIsIn(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        if (p != null && p.isOnline()) {
            String str = " is not in any TARDIS.";
            HashMap<String, Object> where = new HashMap<>();
            where.put("uuid", uuid.toString());
            ResultSetTravellers rs = new ResultSetTravellers(TARDIS.plugin, where, false);
            if (rs.resultSet()) {
                HashMap<String, Object> wheret = new HashMap<>();
                wheret.put("tardis_id", rs.getTardis_id());
                ResultSetTardis rst = new ResultSetTardis(TARDIS.plugin, wheret, "", false, 2);
                if (rst.resultSet()) {
                    if (rst.getTardis().isAbandoned()) {
                        str = " is in an abandoned TARDIS.";
                    } else {
                        str = " is in " + rst.getTardis().getOwner() + "'s TARDIS.";
                    }
                }
            }
            return p.getName() + str;
        }
        return "Player is not online.";
    }

    @Override
    public int getIdOfTARDISPlayerIsIn(Player p) {
        return getIdOfTARDISPlayerIsIn(p.getUniqueId());
    }

    @Override
    public int getIdOfTARDISPlayerIsIn(UUID uuid) {
        HashMap<String, Object> where = new HashMap<>();
        where.put("uuid", uuid.toString());
        ResultSetTravellers rs = new ResultSetTravellers(TARDIS.plugin, where, false);
        if (rs.resultSet()) {
            return rs.getTardis_id();
        }
        return -1;
    }

    @Override
    public List<String> getPlayersInTARDIS(int id) {
        List<String> list = new ArrayList<>();
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        ResultSetTravellers rs = new ResultSetTravellers(TARDIS.plugin, where, true);
        if (rs.resultSet()) {
            rs.getData().forEach((u) -> {
                Player p = Bukkit.getPlayer(u);
                if (p != null && p.isOnline()) {
                    list.add(p.getName());
                }
            });
        }
        return list;
    }

    @Override
    public List<String> getPlayersInTARDIS(Player p) {
        return getPlayersInTARDIS(p.getUniqueId());
    }

    @Override
    public List<String> getPlayersInTARDIS(UUID uuid) {
        ResultSetTardisID rs = new ResultSetTardisID(TARDIS.plugin);
        if (rs.fromUUID(uuid.toString())) {
            return getPlayersInTARDIS(rs.getTardis_id());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getTARDISCompanions(int id) {
        List<String> list = new ArrayList<>();
        ResultSetTardisCompanions rs = new ResultSetTardisCompanions(TARDIS.plugin);
        if (rs.fromID(id)) {
            String companions = rs.getCompanions();
            if (!companions.isEmpty()) {
                for (String s : companions.split(":")) {
                    Player p = Bukkit.getPlayer(s);
                    if (p != null && p.isOnline()) {
                        list.add(p.getName());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getTARDISCompanions(Player p) {
        return getTARDISCompanions(p.getUniqueId());
    }

    @Override
    public List<String> getTARDISCompanions(UUID uuid) {
        List<String> list = new ArrayList<>();
        ResultSetTardisCompanions rs = new ResultSetTardisCompanions(TARDIS.plugin);
        if (rs.fromUUID(uuid.toString())) {
            String companions = rs.getCompanions();
            if (!companions.isEmpty()) {
                for (String s : companions.split(":")) {
                    Player p = Bukkit.getPlayer(s);
                    if (p != null && p.isOnline()) {
                        list.add(p.getName());
                    }
                }
            }
        }
        return list;
    }

    @Override
    public boolean isPlayerInZeroRoom(Player p) {
        return isPlayerInZeroRoom(p.getUniqueId());
    }

    @Override
    public boolean isPlayerInZeroRoom(UUID uuid) {
        return TARDIS.plugin.getTrackerKeeper().getZeroRoomOccupants().contains(uuid);
    }

    @Override
    public boolean isPlayerGeneticallyModified(Player p) {
        return isPlayerGeneticallyModified(p.getUniqueId());
    }

    @Override
    public boolean isPlayerGeneticallyModified(UUID uuid) {
        return TARDIS.plugin.getTrackerKeeper().getGeneticallyModified().contains(uuid);
    }

    @Override
    public TARDISUtils getUtils() {
        return TARDIS.plugin.getUtils();
    }

    @Override
    public TARDISLocationGetters getLocationUtils() {
        return TARDIS.plugin.getLocationUtils();
    }

    @Override
    public TARDISPluginRespect getRespect() {
        return TARDIS.plugin.getPluginRespect();
    }

    @Override
    public HashMap<String, ShapedRecipe> getShapedRecipes() {
        return TARDIS.plugin.getFigura().getShapedRecipes();
    }

    @Override
    public HashMap<String, ShapelessRecipe> getShapelessRecipes() {
        return TARDIS.plugin.getIncomposita().getShapelessRecipes();
    }

    @Override
    public List<String> getWallFloorBlocks() {
        List<String> blocks = new ArrayList<>();
        TARDISWalls.BLOCKS.forEach((m) -> blocks.add(m.toString()));
        return blocks;
    }

    @Override
    public boolean setDestination(int id, Location location, boolean travel) {
        // get current direction
        HashMap<String, Object> wherec = new HashMap<>();
        wherec.put("tardis_id", id);
        ResultSetCurrentLocation rs = new ResultSetCurrentLocation(TARDIS.plugin, wherec);
        if (rs.resultSet()) {
            HashMap<String, Object> where = new HashMap<>();
            where.put("tardis_id", id);
            HashMap<String, Object> set = new HashMap<>();
            set.put("world", location.getWorld().getName());
            set.put("x", location.getBlockX());
            set.put("y", location.getBlockY());
            set.put("z", location.getBlockZ());
            set.put("direction", rs.getDirection().toString());
            set.put("submarine", 0);
            new QueryFactory(TARDIS.plugin).doUpdate("next", set, where);
            if (travel) {
                // get TARDIS data
                HashMap<String, Object> wheret = new HashMap<>();
                wheret.put("tardis_id", id);
                ResultSetTardis rst = new ResultSetTardis(TARDIS.plugin, wheret, "", false, 2);
                if (rst.resultSet()) {
                    Player player = Bukkit.getServer().getPlayer(rst.getTardis().getUuid());
                    // travel
                    TARDIS.plugin.getTrackerKeeper().getHasDestination().put(id, TARDIS.plugin.getArtronConfig().getInt("random"));
                    new TARDISTakeoff(TARDIS.plugin).run(id, player, rst.getTardis().getBeacon());
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setDestination(UUID uuid, Location location, boolean travel) {
        // get tardis_id
        ResultSetTardisID rst = new ResultSetTardisID(TARDIS.plugin);
        if (rst.fromUUID(uuid.toString())) {
            return setDestination(rst.getTardis_id(), location, travel);
        } else {
            return false;
        }
    }

    @Override
    public boolean setDestination(Player player, Location location, boolean travel) {
        return setDestination(player.getUniqueId(), location, travel);
    }

    @Override
    public Tardis getTardisData(int id) {
        // get TARDIS data
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        ResultSetTardis rs = new ResultSetTardis(TARDIS.plugin, where, "", false, 2);
        if (rs.resultSet()) {
            return rs.getTardis();
        } else {
            return null;
        }
    }

    @Override
    public Tardis getTardisData(UUID uuid) {
        // get TARDIS data
        HashMap<String, Object> where = new HashMap<>();
        where.put("uuid", uuid.toString());
        ResultSetTardis rs = new ResultSetTardis(TARDIS.plugin, where, "", false, 2);
        if (rs.resultSet()) {
            return rs.getTardis();
        } else {
            return null;
        }
    }

    @Override
    public Tardis getTardisData(Player player) {
        return getTardisData(player.getUniqueId());
    }

    @Override
    public boolean setChameleonPreset(int id, PRESET preset, boolean rebuild) {
        // check not travelling
        TARDISTrackerInstanceKeeper keeper = TARDIS.plugin.getTrackerKeeper();
        if (keeper.getDematerialising().contains(id) || keeper.getMaterialising().contains(id) || keeper.getDestinationVortex().containsKey(id) || keeper.getInVortex().contains(id)) {
            return false;
        }
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        HashMap<String, Object> set = new HashMap<>();
        set.put("chameleon_preset", preset.toString());
        new QueryFactory(TARDIS.plugin).doSyncUpdate("tardis", set, where);
        if (rebuild) {
            // rebuild exterior
            HashMap<String, Object> wherecl = new HashMap<>();
            wherecl.put("tardis_id", id);
            ResultSetCurrentLocation rsc = new ResultSetCurrentLocation(TARDIS.plugin, wherecl);
            if (!rsc.resultSet()) {
                return false;
            }
            HashMap<String, Object> wheret = new HashMap<>();
            wheret.put("tardis_id", id);
            ResultSetTardis rs = new ResultSetTardis(TARDIS.plugin, wheret, "", false, 2);
            if (rs.resultSet()) {
                OfflinePlayer player = Bukkit.getOfflinePlayer(rs.getTardis().getUuid());
                Location l = new Location(rsc.getWorld(), rsc.getX(), rsc.getY(), rsc.getZ());
                BuildData bd = new BuildData(TARDIS.plugin, player.getUniqueId().toString());
                bd.setDirection(rsc.getDirection());
                bd.setLocation(l);
                bd.setMalfunction(false);
                bd.setOutside(false);
                bd.setPlayer(player);
                bd.setRebuild(true);
                bd.setSubmarine(false);
                bd.setTardisID(id);
                bd.setBiome(rsc.getBiome());
                TARDIS.plugin.getPresetBuilder().buildPreset(bd);
                TARDIS.plugin.getTrackerKeeper().getInVortex().add(id);
                HashMap<String, Object> whereh = new HashMap<>();
                whereh.put("tardis_id", id);
                HashMap<String, Object> seth = new HashMap<>();
                seth.put("hidden", 0);
                new QueryFactory(TARDIS.plugin).doUpdate("tardis", seth, whereh);
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean setChameleonPreset(UUID uuid, PRESET preset, boolean rebuild) {
        // get tardis_id
        ResultSetTardisID rst = new ResultSetTardisID(TARDIS.plugin);
        if (rst.fromUUID(uuid.toString())) {
            return setChameleonPreset(rst.getTardis_id(), preset, rebuild);
        } else {
            return false;
        }
    }

    @Override
    public boolean setChameleonPreset(Player player, PRESET preset, boolean rebuild) {
        return setChameleonPreset(player.getUniqueId(), preset, rebuild);
    }

    @Override
    public void spawnAbandonedTARDIS(Location location, String type, PRESET preset, COMPASS direction) throws TARDISException {
        if (!CONSOLES.getBY_NAMES().containsKey(type.toUpperCase(Locale.ENGLISH))) {
            throw new TARDISException("Not a valid Console type");
        }
        if (!TARDIS.plugin.getConfig().getBoolean("abandon.enabled")) {
            throw new TARDISException("Abandoned TARDISes are not allowed on this server");
        }
        if (!TARDIS.plugin.getConfig().getBoolean("creation.default_world")) {
            throw new TARDISException("TARDIS must be configured to create TARDISes in a default world");
        }
        SCHEMATIC schm = CONSOLES.getBY_NAMES().get(type.toUpperCase(Locale.ENGLISH));
        new TARDISAbandoned(TARDIS.plugin).spawn(location, schm, preset, direction);
    }

    @Override
    public void spawnAbandonedTARDIS(Location location) {
        try {
            spawnAbandonedTARDIS(location, "BUDGET", PRESET.FACTORY, COMPASS.SOUTH);
        } catch (TARDISException ex) {
            Bukkit.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String setDesktopWallAndFloor(int id, String wall, String floor, boolean artron) {
        // get uuid
        HashMap<String, Object> where = new HashMap<>();
        where.put("tardis_id", id);
        ResultSetTardis rst = new ResultSetTardis(TARDIS.plugin, where, "", false, 2);
        if (rst.resultSet()) {
            try {
                return setDesktopWallAndFloor(rst.getTardis().getUuid(), wall, floor, artron);
            } catch (TARDISException ex) {
                Bukkit.getLogger().log(Level.SEVERE, null, ex);
                return "";
            }
        } else {
            return "";
        }
    }

    @Override
    public String setDesktopWallAndFloor(UUID uuid, String wall, String floor, boolean artron) throws TARDISException {
        Material w = Material.getMaterial(wall);
        Material f = Material.getMaterial(floor);
        if (!TARDISWalls.BLOCKS.contains(w)) {
            throw new TARDISException("Not a valid wall type");
        }
        if (!TARDISWalls.BLOCKS.contains(f)) {
            throw new TARDISException("Not a valid wall type");
        }
        // get current SCHEMATIC
        HashMap<String, Object> where = new HashMap<>();
        where.put("uuid", uuid.toString());
        ResultSetTardis rs = new ResultSetTardis(TARDIS.plugin, where, "", false, 2);
        if (rs.resultSet()) {
            SCHEMATIC current_console = rs.getTardis().getSchematic();
            TARDISUpgradeData tud = new TARDISUpgradeData();
            tud.setSchematic(current_console);
            tud.setPrevious(current_console);
            tud.setLevel(rs.getTardis().getArtron_level());
            tud.setWall(wall);
            tud.setFloor(floor);
            // change the wall and floor
            TARDISWallFloorRunnable ttr = new TARDISWallFloorRunnable(TARDIS.plugin, uuid, tud);
            long delay = Math.round(20 / TARDIS.plugin.getConfig().getDouble("growth.room_speed"));
            int task = TARDIS.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(TARDIS.plugin, ttr, 5L, delay);
            ttr.setTaskID(task);
            HashMap<String, Object> wherep = new HashMap<>();
            wherep.put("uuid", uuid.toString());
            ResultSetPlayerPrefs rsp = new ResultSetPlayerPrefs(TARDIS.plugin, wherep);
            if (rsp.resultSet()) {
                return rsp.getWall() + "," + rsp.getFloor();
            } else {
                return "ORANGE_WOOL,LIGHT_GRAY_WOOL";
            }
        } else {
            return "";
        }
    }
}
