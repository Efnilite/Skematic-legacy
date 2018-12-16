package com.efnilite.skematic.utils;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.World;
import org.bukkit.entity.Player;

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

}
