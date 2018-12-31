package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Shape - Cylinder")
@Description("Create a cylinder at a location")
@Examples("create a cylinder at {_location} with pattern grass, glass and stone and radius 1 and height 2")
public class EffShapeCylinder extends SkematicEffect {

    static {
        Skript.registerEffect(EffShapeCylinder.class, "(make|create) [a] [new] [(1¦hollow)] cylinder at %location% (with|and) [(itemtypes[s]|block[s]|pattern)] %itemtypes% (with|and) radius %number% (with|and) height %number%");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        Location location = Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1]).getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[2].getAll(e);
        Number radius = (Number) expressions[3].getSingle(e);
        Number height = (Number) expressions[4].getSingle(e);

        boolean filled = true;

        if (blocks == null || radius == null || location == null | height == null) {
            return;
        }

        if (mark == 1) {
            filled = false;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.makeCylinder(FaweUtils.toVector(location), FaweUtils.parsePattern(blocks), Math.round((long) radius), Math.round((long) height), filled);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create cylinder at " + expressions[0].toString(e, debug) + " with blocks " + expressions[1].toString(e, debug) + " with radius " + expressions[2].toString(e, debug) + " and height " + expressions[3].toString(e, debug);
    }
}
