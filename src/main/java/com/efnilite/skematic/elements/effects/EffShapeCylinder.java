package com.efnilite.skematic.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Shape - Cylinder")
@Description("Create a cylinder at a location")
@Examples("create a cylinder at {_location} with pattern grass, glass and stone and radius 1 and height 2")
@Patterns("(make|create) [a] [new] [(1¦hollow)] cylinder at %location% (with|and) [(itemtypes[s]|block[s]|pattern)] %itemtypes% (with|and) radius %number% (with|and) height %numer%")
public class EffShapeCylinder extends SkematicEffect {

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        Location location = (Location) expressions[0].getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[1].getAll(e);
        Number radius = (Number) expressions[2].getSingle(e);
        Number height = (Number) expressions[3].getSingle(e);

        boolean filled = true;

        if (location == null || blocks == null || radius == null) {
            return;
        }

        if (mark == 1) {
            filled = false;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.makeCylinder(FaweTools.toVector(location), FaweTools.parsePattern(blocks), Math.round((long) radius), Math.round((long) height), filled);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create cylinder at " + expressions[0].toString(e, debug) + " with blocks " + expressions[1].toString(e, debug) + " with radius " + expressions[2].toString(e, debug) + " and height " + expressions[3].toString(e, debug);
    }
}
