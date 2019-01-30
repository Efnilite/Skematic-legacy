package com.efnilite.skematic.utils;

import ch.njol.skript.aliases.ItemType;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class FaweUtils {

    /**
     * Gets a WE world from a String
     * @param s the world name
     * @return the WE world
     */
    public static World getWorld(String s) {
        return FaweAPI.getWorld(s);
    }

    /**
     * Gets a FAWE player from a Bukkit Player
     * @param o a Bukkit Player
     * @return FAWE player
     */
    public static FawePlayer getPlayer(Player o) {
        return FaweAPI.wrapPlayer(o);
    }

    /**
     * Gets an editsession in a world
     * @param w the world the editsession is gonna be in
     * @return the editsession
     */
    public static EditSession getEditSession(org.bukkit.World w) {
        return FaweAPI.getEditSessionBuilder(getWorld(w.getName())).autoQueue(true).build();
    }

    /**
     * Turns a location into a Vector
     * @param location the location
     * @return the Vector of the location
     */
    public static Vector toVector(Location location) {
        return new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    /**
     * Parses a pattern so that it can be used in shapes
     * @param blocks the itemtypes that the pattern has to contain
     * @return the parsed pattern from param blocks
     */
    public static Pattern parsePattern(ItemType[] blocks) {
        RandomPattern parsedPattern = new RandomPattern();
        for (ItemType b : blocks) {
            if (b.getRandom().getType().isBlock()) {
                parsedPattern.add(new BlockPattern(new BaseBlock(b.getRandom().getType().getId(), b.getRandom().getDurability())), 50);
            }
        }
        return parsedPattern;
    }

    /**
     * Gets a Schematic from a file
     * @param file the file needing to change to schematic
     * @return a schematic
     */
    public static Schematic toSchematic(File file) {
        try {
            return new Schematic(FaweAPI.load(file).getClipboard());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Gets a Schematic from a cuboid
     * @param cuboid the cuboid needing to be changed
     * @return a schematic
     */
    public static Schematic toSchematic(CuboidRegion cuboid) {
        return new Schematic(cuboid);
    }

}
