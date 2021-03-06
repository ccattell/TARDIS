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
package me.eccentric_nz.TARDIS;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * One of the key features of a TARDIS is that the interior exists in a dimension different from the exterior. The main
 * application of this concept is that they are bigger on the inside than the outside.
 *
 * @author eccentric_nz
 */
public class TARDISConstants {

    // TARDIS world names
    public static final List<String> PLANETS = Arrays.asList("Gallifrey", "Siluria", "Skaro");
    public static final ImmutableMap<String, String> REVERT_LEVELS = ImmutableMap.of("gallifrey", "Gallifrey", "siluria", "Siluria", "skaro", "Skaro", "tardis_time_vortex", "TARDIS_TimeVortex", "tardis_zero_room", "TARDIS_Zero_Room");

    // consoles which need to be higher
    public static final List<String> HIGHER = Arrays.asList("bigger", "redstone", "twelfth", "thirteenth", "factory");

    // GUI materials
    public static final List<Material> GUI_IDS = Arrays.asList(Material.STONE, Material.GRASS_BLOCK, Material.OAK_PLANKS, Material.BEDROCK, Material.SAND, Material.GRAVEL, Material.GOLD_ORE, Material.IRON_ORE, Material.COAL_ORE, Material.OAK_LOG, Material.OAK_LEAVES, Material.SPONGE, Material.GLASS, Material.LAPIS_ORE, Material.LAPIS_BLOCK, Material.DISPENSER, Material.SANDSTONE, Material.PISTON, Material.PINK_WOOL, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.BRICK, Material.TNT, Material.BOOKSHELF, Material.MOSSY_COBBLESTONE, Material.OBSIDIAN, Material.SPAWNER, Material.DIAMOND_ORE, Material.DIAMOND_BLOCK, Material.CRAFTING_TABLE, Material.FURNACE, Material.REDSTONE_ORE, Material.ICE, Material.SNOW_BLOCK, Material.CACTUS, Material.CLAY, Material.JUKEBOX, Material.PUMPKIN, Material.NETHERRACK, Material.SOUL_SAND, Material.GLOWSTONE, Material.STONE_BRICKS, Material.RED_MUSHROOM_BLOCK, Material.BROWN_MUSHROOM_BLOCK, Material.MELON, Material.MYCELIUM, Material.NETHER_BRICKS, Material.CAULDRON, Material.END_STONE, Material.REDSTONE_LAMP, Material.EMERALD_ORE, Material.EMERALD_BLOCK, Material.NETHER_QUARTZ_ORE, Material.QUARTZ_BLOCK);
    // chameleon blocks
    public static final List<Material> CHAMELEON_BLOCKS_VALID = Arrays.asList(Material.STONE, Material.DIRT, Material.COBBLESTONE, Material.OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.BIRCH_PLANKS, Material.SPRUCE_PLANKS, Material.JUNGLE_PLANKS, Material.ACACIA_PLANKS, Material.BEDROCK, Material.GOLD_ORE, Material.IRON_ORE, Material.COAL_ORE, Material.OAK_LOG, Material.DARK_OAK_LOG, Material.BIRCH_LOG, Material.SPRUCE_LOG, Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.OAK_LEAVES, Material.DARK_OAK_LEAVES, Material.BIRCH_LEAVES, Material.SPRUCE_LEAVES, Material.JUNGLE_LEAVES, Material.ACACIA_LEAVES, Material.SPONGE, Material.GLASS, Material.LAPIS_ORE, Material.LAPIS_BLOCK, Material.SANDSTONE, Material.NOTE_BLOCK, Material.WHITE_WOOL, Material.ORANGE_WOOL, Material.LIGHT_BLUE_WOOL, Material.MAGENTA_WOOL, Material.YELLOW_WOOL, Material.LIME_WOOL, Material.PINK_WOOL, Material.CYAN_WOOL, Material.GRAY_WOOL, Material.LIGHT_GRAY_WOOL, Material.PURPLE_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.GREEN_WOOL, Material.RED_WOOL, Material.BLACK_WOOL, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.BRICK, Material.TNT, Material.BOOKSHELF, Material.MOSSY_COBBLESTONE, Material.OBSIDIAN, Material.DIAMOND_ORE, Material.DIAMOND_BLOCK, Material.CRAFTING_TABLE, Material.REDSTONE_ORE, Material.ICE, Material.SNOW_BLOCK, Material.CLAY, Material.JUKEBOX, Material.PUMPKIN, Material.NETHERRACK, Material.SOUL_SAND, Material.GLOWSTONE, Material.JACK_O_LANTERN, Material.STONE_BRICKS, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.MELON, Material.MYCELIUM, Material.NETHER_BRICKS, Material.END_STONE, Material.REDSTONE_LAMP, Material.EMERALD_ORE, Material.EMERALD_BLOCK, Material.QUARTZ_BLOCK, Material.WHITE_TERRACOTTA, Material.ORANGE_TERRACOTTA, Material.LIGHT_BLUE_TERRACOTTA, Material.MAGENTA_TERRACOTTA, Material.YELLOW_TERRACOTTA, Material.LIME_TERRACOTTA, Material.PINK_TERRACOTTA, Material.CYAN_TERRACOTTA, Material.GRAY_TERRACOTTA, Material.LIGHT_GRAY_TERRACOTTA, Material.PURPLE_TERRACOTTA, Material.BLUE_TERRACOTTA, Material.BROWN_TERRACOTTA, Material.GREEN_TERRACOTTA, Material.RED_TERRACOTTA, Material.BLACK_TERRACOTTA, Material.HAY_BLOCK, Material.TERRACOTTA, Material.COAL_BLOCK, Material.PACKED_ICE);
    public static final List<Material> CHAMELEON_BLOCKS_BAD = Arrays.asList(Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.JUNGLE_SAPLING, Material.ACACIA_SAPLING, Material.WATER, Material.LAVA, Material.DISPENSER, Material.WHITE_BED, Material.ORANGE_BED, Material.LIGHT_BLUE_BED, Material.MAGENTA_BED, Material.YELLOW_BED, Material.LIME_BED, Material.PINK_BED, Material.CYAN_BED, Material.GRAY_BED, Material.LIGHT_GRAY_BED, Material.PURPLE_BED, Material.BLUE_BED, Material.BROWN_BED, Material.GREEN_BED, Material.RED_BED, Material.BLACK_BED, Material.STICKY_PISTON, Material.PISTON, Material.PISTON_HEAD, Material.TORCH, Material.FIRE, Material.SPAWNER, Material.CHEST, Material.REDSTONE_WIRE, Material.WHEAT, Material.FURNACE, Material.OAK_SIGN, Material.DARK_OAK_SIGN, Material.SPRUCE_SIGN, Material.BIRCH_SIGN, Material.JUNGLE_SIGN, Material.ACACIA_SIGN, Material.OAK_DOOR, Material.DARK_OAK_DOOR, Material.BIRCH_DOOR, Material.SPRUCE_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.LADDER, Material.OAK_WALL_SIGN, Material.DARK_OAK_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.JUNGLE_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.STONE_PRESSURE_PLATE, Material.IRON_DOOR, Material.OAK_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.ACACIA_PRESSURE_PLATE, Material.REDSTONE_TORCH, Material.STONE_BUTTON, Material.SUGAR_CANE, Material.OAK_FENCE, Material.DARK_OAK_FENCE, Material.BIRCH_FENCE, Material.SPRUCE_FENCE, Material.JUNGLE_FENCE, Material.ACACIA_FENCE, Material.NETHER_PORTAL, Material.CAKE, Material.REPEATER, Material.IRON_BARS, Material.OAK_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.LILY_PAD, Material.NETHER_WART, Material.ENCHANTING_TABLE, Material.BREWING_STAND, Material.CAULDRON, Material.END_PORTAL, Material.DRAGON_EGG, Material.COCOA, Material.TRIPWIRE_HOOK, Material.TRIPWIRE);
    private static final Material[] CHAMELEON_BLOCKS_CHANGE_ARR = {Material.ACACIA_SLAB, Material.ACACIA_STAIRS, Material.ACACIA_TRAPDOOR, Material.ATTACHED_MELON_STEM, Material.ATTACHED_PUMPKIN_STEM, Material.BIRCH_SLAB, Material.BIRCH_STAIRS, Material.BIRCH_TRAPDOOR, Material.BRICK_STAIRS, Material.CACTUS, Material.COBBLESTONE_SLAB, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_SLAB, Material.DARK_OAK_STAIRS, Material.DARK_OAK_TRAPDOOR, Material.END_PORTAL_FRAME, Material.FARMLAND, Material.GRASS_BLOCK, Material.GRAVEL, Material.INFESTED_CHISELED_STONE_BRICKS, Material.INFESTED_COBBLESTONE, Material.INFESTED_CRACKED_STONE_BRICKS, Material.INFESTED_MOSSY_STONE_BRICKS, Material.INFESTED_STONE_BRICKS, Material.INFESTED_STONE, Material.IRON_BARS, Material.JUNGLE_SLAB, Material.JUNGLE_STAIRS, Material.JUNGLE_TRAPDOOR, Material.MELON_STEM, Material.NETHER_BRICK_FENCE, Material.NETHER_BRICK_STAIRS, Material.OAK_SLAB, Material.OAK_STAIRS, Material.OAK_TRAPDOOR, Material.PUMPKIN_STEM, Material.PURPUR_SLAB, Material.PURPUR_STAIRS, Material.QUARTZ_SLAB, Material.QUARTZ_STAIRS, Material.RED_SAND, Material.RED_SANDSTONE_SLAB, Material.RED_SANDSTONE_STAIRS, Material.SAND, Material.SANDSTONE_SLAB, Material.SANDSTONE_STAIRS, Material.SNOW, Material.SPRUCE_SLAB, Material.SPRUCE_STAIRS, Material.SPRUCE_TRAPDOOR, Material.STONE_BRICK_SLAB, Material.STONE_BRICK_STAIRS, Material.VINE};
    private static final Material[] CHAMELEON_BLOCKS_CHANGE_TO_ARR = {Material.ACACIA_PLANKS, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS, Material.MELON, Material.PUMPKIN, Material.BIRCH_PLANKS, Material.BIRCH_PLANKS, Material.BIRCH_PLANKS, Material.BRICKS, Material.SANDSTONE, Material.COBBLESTONE, Material.COBBLESTONE, Material.DARK_OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.DARK_OAK_PLANKS, Material.END_STONE, Material.DIRT, Material.DIRT, Material.STONE, Material.CHISELED_STONE_BRICKS, Material.COBBLESTONE, Material.CRACKED_STONE_BRICKS, Material.MOSSY_STONE_BRICKS, Material.STONE_BRICKS, Material.STONE, Material.GLASS, Material.JUNGLE_PLANKS, Material.JUNGLE_PLANKS, Material.JUNGLE_PLANKS, Material.MELON, Material.NETHER_BRICKS, Material.NETHER_BRICKS, Material.OAK_PLANKS, Material.OAK_PLANKS, Material.OAK_PLANKS, Material.PUMPKIN, Material.PURPUR_BLOCK, Material.PURPUR_BLOCK, Material.QUARTZ_BLOCK, Material.QUARTZ_BLOCK, Material.RED_SANDSTONE, Material.RED_SANDSTONE, Material.RED_SANDSTONE, Material.SANDSTONE, Material.SANDSTONE, Material.SANDSTONE, Material.SNOW_BLOCK, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS, Material.SPRUCE_PLANKS, Material.STONE_BRICKS, Material.STONE_BRICKS, Material.OAK_LEAVES};
    public static final List<Material> CHAMELEON_BLOCKS_CHANGE = Arrays.asList(Material.ACACIA_SLAB, Material.ACACIA_STAIRS, Material.ACACIA_TRAPDOOR, Material.ATTACHED_MELON_STEM, Material.ATTACHED_PUMPKIN_STEM, Material.BIRCH_SLAB, Material.BIRCH_STAIRS, Material.BIRCH_TRAPDOOR, Material.BRICK_STAIRS, Material.CACTUS, Material.COBBLESTONE_SLAB, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_SLAB, Material.DARK_OAK_STAIRS, Material.DARK_OAK_TRAPDOOR, Material.END_PORTAL_FRAME, Material.FARMLAND, Material.GRASS_BLOCK, Material.GRAVEL, Material.INFESTED_CHISELED_STONE_BRICKS, Material.INFESTED_COBBLESTONE, Material.INFESTED_CRACKED_STONE_BRICKS, Material.INFESTED_MOSSY_STONE_BRICKS, Material.INFESTED_STONE_BRICKS, Material.INFESTED_STONE, Material.IRON_BARS, Material.JUNGLE_SLAB, Material.JUNGLE_STAIRS, Material.JUNGLE_TRAPDOOR, Material.MELON_STEM, Material.NETHER_BRICK_FENCE, Material.NETHER_BRICK_STAIRS, Material.OAK_SLAB, Material.OAK_STAIRS, Material.OAK_TRAPDOOR, Material.PUMPKIN_STEM, Material.PURPUR_SLAB, Material.PURPUR_STAIRS, Material.QUARTZ_SLAB, Material.QUARTZ_STAIRS, Material.RED_SAND, Material.RED_SANDSTONE_SLAB, Material.RED_SANDSTONE_STAIRS, Material.SAND, Material.SANDSTONE_SLAB, Material.SANDSTONE_STAIRS, Material.SNOW, Material.SPRUCE_SLAB, Material.SPRUCE_STAIRS, Material.SPRUCE_TRAPDOOR, Material.STONE_BRICK_SLAB, Material.STONE_BRICK_STAIRS, Material.VINE);
    public static final HashMap<Material, Material> CHAMELEON_BLOCKS_CHANGE_HASH = toMap(CHAMELEON_BLOCKS_CHANGE_ARR, CHAMELEON_BLOCKS_CHANGE_TO_ARR);
    public static final List<Material> CHAMELEON_BLOCKS_NEXT = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.COBWEB, Material.TALL_GRASS, Material.DEAD_BUSH, Material.DANDELION, Material.ALLIUM, Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.FERN, Material.GRASS, Material.LARGE_FERN, Material.LILAC, Material.ORANGE_TULIP, Material.OXEYE_DAISY, Material.PEONY, Material.PINK_TULIP, Material.POPPY, Material.RED_TULIP, Material.ROSE_BUSH, Material.SUNFLOWER, Material.WHITE_TULIP, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.RAIL, Material.DAYLIGHT_DETECTOR);
    public static final List<Material> CHAMELEON_BLOCKS_PRECIOUS = Arrays.asList(Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.TNT, Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK, Material.REDSTONE_BLOCK, Material.COAL_BLOCK);
    public static final List<String> VOWELS = Arrays.asList("A", "E", "I", "O", "U");
    public static final List<EntityType> ENTITY_TYPES = Arrays.asList(EntityType.BAT, EntityType.BLAZE, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.GHAST, EntityType.GIANT, EntityType.GUARDIAN, EntityType.HORSE, EntityType.HUSK, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MAGMA_CUBE, EntityType.MULE, EntityType.MUSHROOM_COW, EntityType.OCELOT, EntityType.PARROT, EntityType.PHANTOM, EntityType.PIG_ZOMBIE, EntityType.PIG, EntityType.PLAYER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON_HORSE, EntityType.SKELETON, EntityType.SLIME, EntityType.SNOWMAN, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VEX, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WITCH, EntityType.WITHER, EntityType.WOLF, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE);
    public static final List<EntityType> MONSTER_TYPES = Arrays.asList(EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.HUSK, EntityType.ILLUSIONER, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SKELETON, EntityType.SLIME, EntityType.SPIDER, EntityType.STRAY, EntityType.VEX, EntityType.VINDICATOR, EntityType.WITCH, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIE);
    public static final List<Biome> NO_RAIN = Arrays.asList(Biome.DESERT, Biome.DESERT_HILLS, Biome.DESERT_LAKES, Biome.NETHER, Biome.SAVANNA, Biome.SHATTERED_SAVANNA, Biome.BADLANDS, Biome.BADLANDS_PLATEAU, Biome.ERODED_BADLANDS, Biome.SHATTERED_SAVANNA_PLATEAU, Biome.SAVANNA_PLATEAU, Biome.MODIFIED_BADLANDS_PLATEAU, Biome.MODIFIED_WOODED_BADLANDS_PLATEAU, Biome.WOODED_BADLANDS_PLATEAU, Biome.DEEP_FROZEN_OCEAN, Biome.FROZEN_OCEAN, Biome.FROZEN_RIVER, Biome.SNOWY_TUNDRA, Biome.SNOWY_MOUNTAINS, Biome.ICE_SPIKES, Biome.SNOWY_BEACH, Biome.SNOWY_TAIGA, Biome.SNOWY_TAIGA_HILLS, Biome.SNOWY_TAIGA_MOUNTAINS);
    public static final List<Biome> END_BIOMES = Arrays.asList(Biome.END_BARRENS, Biome.END_HIGHLANDS, Biome.END_MIDLANDS, Biome.THE_END, Biome.SMALL_END_ISLANDS);
    public static final List<Material> GOOD_MATERIALS = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.BROWN_MUSHROOM, Material.BROWN_MUSHROOM_BLOCK, Material.DEAD_BUSH, Material.ALLIUM, Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.FERN, Material.GRASS, Material.LARGE_FERN, Material.LILAC, Material.ORANGE_TULIP, Material.OXEYE_DAISY, Material.PEONY, Material.PINK_TULIP, Material.POPPY, Material.RED_TULIP, Material.ROSE_BUSH, Material.SUNFLOWER, Material.WHITE_TULIP, Material.TALL_GRASS, Material.NETHER_WART, Material.RED_MUSHROOM, Material.ROSE_BUSH, Material.OAK_SAPLING, Material.DARK_OAK_SAPLING, Material.BIRCH_SAPLING, Material.SPRUCE_SAPLING, Material.JUNGLE_SAPLING, Material.ACACIA_SAPLING, Material.SNOW, Material.DANDELION);
    public static final List<Material> GOOD_WATER = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR, Material.WATER, Material.SEAGRASS, Material.TALL_SEAGRASS, Material.KELP_PLANT, Material.SEA_PICKLE);
    public static final List<String> CIRCUITS = Arrays.asList("Bio-scanner Circuit", "Diamond Disruptor Circuit", "Emerald Environment Circuit", "Ignite Circuit", "Painter Circuit", "Perception Circuit", "Pickup Arrows Circuit", "Redstone Activator Circuit", "Rift Circuit", "Server Admin Circuit", "Sonic Oscillator", "TARDIS ARS Circuit", "TARDIS Chameleon Circuit", "TARDIS Input Circuit", "TARDIS Invisibility Circuit", "TARDIS Locator Circuit", "TARDIS Materialisation Circuit", "TARDIS Memory Circuit", "TARDIS Randomiser Circuit", "TARDIS Scanner Circuit", "TARDIS Stattenheim Circuit", "TARDIS Telepathic Circuit", "TARDIS Temporal Circuit");
    public static final BlockData AIR = Material.AIR.createBlockData();
    public static final BlockData VOID_AIR = Material.VOID_AIR.createBlockData();
    public static final BlockData FIRE = Material.FIRE.createBlockData();
    public static final BlockData LAMP = Bukkit.createBlockData("minecraft:redstone_lamp[lit=true]");
    public static final BlockData SNOW = Material.SNOW.createBlockData();
    public static final BlockData GLASS = Material.GLASS.createBlockData();
    public static final BlockData POWER = Material.REDSTONE_BLOCK.createBlockData();
    public static final BlockData LANTERN = Material.SEA_LANTERN.createBlockData();
    public static final BlockData BLACK = Material.BLACK_WOOL.createBlockData();
    public static final BlockData DAYLIGHT = Material.DAYLIGHT_DETECTOR.createBlockData();
    public static final List<LootTable> LOOT = Arrays.asList(LootTables.ABANDONED_MINESHAFT.getLootTable(), LootTables.BURIED_TREASURE.getLootTable(), LootTables.DESERT_PYRAMID.getLootTable(), LootTables.IGLOO_CHEST.getLootTable(), LootTables.JUNGLE_TEMPLE.getLootTable(), LootTables.SHIPWRECK_TREASURE.getLootTable(), LootTables.SIMPLE_DUNGEON.getLootTable(), LootTables.SPAWN_BONUS_CHEST.getLootTable(), LootTables.STRONGHOLD_LIBRARY.getLootTable(), LootTables.VILLAGE_ARMORER.getLootTable(), LootTables.VILLAGE_BUTCHER.getLootTable(), LootTables.VILLAGE_CARTOGRAPHER.getLootTable(), LootTables.VILLAGE_DESERT_HOUSE.getLootTable(), LootTables.VILLAGE_FISHER.getLootTable(), LootTables.VILLAGE_FLETCHER.getLootTable(), LootTables.VILLAGE_MASON.getLootTable(), LootTables.VILLAGE_PLAINS_HOUSE.getLootTable(), LootTables.VILLAGE_SAVANNA_HOUSE.getLootTable(), LootTables.VILLAGE_SHEPHERD.getLootTable(), LootTables.VILLAGE_SNOWY_HOUSE.getLootTable(), LootTables.VILLAGE_TAIGA_HOUSE.getLootTable(), LootTables.VILLAGE_TANNERY.getLootTable(), LootTables.VILLAGE_TEMPLE.getLootTable(), LootTables.VILLAGE_TOOLSMITH.getLootTable(), LootTables.VILLAGE_WEAPONSMITH.getLootTable(), LootTables.WOODLAND_MANSION.getLootTable(), LootTables.PILLAGER_OUTPOST.getLootTable());
    public static final Random RANDOM = new Random();

    /**
     * Creates a HashMap from two arrays. The resulting map is used by the chameleon circuit to change unsuitable blocks
     * into more aesthetically pleasing or robust ones i.e. GRASS_BLOCK -> DIRT, SAND -> SANDSTONE
     *
     * @param keys   an array of block types to change
     * @param values an array of block types to change to
     * @return the combined arrays as a HashMap
     */
    private static HashMap<Material, Material> toMap(Material[] keys, Material[] values) {
        int keysSize = (keys != null) ? keys.length : 0;
        int valuesSize = (values != null) ? values.length : 0;
        if (keysSize == 0 && valuesSize == 0) {
            return new HashMap<>();
        }
        if (keysSize != valuesSize) {
            throw new IllegalArgumentException("The number of keys doesn't match the number of values.");
        }
        HashMap<Material, Material> map = new HashMap<>();
        for (int i = 0; i < keysSize; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }
}
