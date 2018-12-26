package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Fill walls")
@Description("Fill the walls of a cuboidregion with a pattern")
public class EffCuboidWalls extends SkematicEffect {

    static {
        Skript.registerEffect(EffCuboidWalls.class, "fill walls of %cuboidregions% with %itemtypes%",
                "fill %cuboidregions%'[s] walls with %itemtypes%");
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[1].getAll(e);

        if (blocks == null || cuboid == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.makeCuboidWalls(cuboid, FaweTools.parsePattern(blocks));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fill walls of " + expressions[0].toString(e, debug) + " with pattern " + expressions[1].toString(e, debug);
    }
}
