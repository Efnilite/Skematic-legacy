package com.efnilite.skematic.utils;

import ch.njol.skript.aliases.ItemType;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.Patterns;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@SuppressWarnings("deprecation")
public class FaweTools {

    public static World getWorld(String s) {
        return FaweAPI.getWorld(s);
    }

    public static FawePlayer getPlayer(Player o) {
        return FaweAPI.wrapPlayer(o);
    }

    public static EditSession getEditSession(org.bukkit.World w) {
        return FaweAPI.getEditSessionBuilder(getWorld(w.getName())).autoQueue(true).build();
    }

    public static Pattern parsePattern(ItemType[] blocks) {
        RandomPattern parsedPattern = new RandomPattern();
        for (ItemType b : blocks) {
            if (b.getRandom().getType().isBlock()) {
                parsedPattern.add(new BlockPattern(new BaseBlock(b.getRandom().getType().getId(), b.getRandom().getDurability())), 50);
            }
        }
        return Patterns.wrap(parsedPattern);
    }

    public static Vector toVector(Location location) {
        return new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
