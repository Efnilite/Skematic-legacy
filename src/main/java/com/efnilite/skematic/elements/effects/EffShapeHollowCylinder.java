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

@Name("Shape - Hollow cylinder")
@Description("Create a hollow cylinder at a location.")
@Examples("make a new hollow cylinder at player's location with blocks grass and dirt with x radius 2 and z radius 2 and thickness 1 and height 4")
@Patterns("(make|create) [a] [new] hollow cylinder at %location% (with|and) [(itemtypes[s]|block[s]|pattern)] %itemtypes% (with|and) x[(-| )]radius %number% (with|and) z[(-| )]radius %number% (with|and) thickness %number% (with|and) height %numer%")
public class EffShapeHollowCylinder extends SkematicEffect {

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        Location location = (Location) expressions[0].getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[1].getAll(e);
        Number x = (Number) expressions[2].getSingle(e);
        Number z = (Number) expressions[3].getSingle(e);
        Number thickness = (Number) expressions[4].getSingle(e);
        Number height = (Number) expressions[5].getSingle(e);

        if (location == null || blocks == null || x == null || z == null || thickness == null || height == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.makeHollowCylinder(FaweTools.toVector(location), FaweTools.parsePattern(blocks), (Double) x, (Double) z, Math.round((long) height), (Double) thickness);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create hollow cylinder at " + expressions[0].toString(e, debug) + " with blocks " + expressions[1].toString(e, debug) + " with x " + expressions[2].toString(e, debug) + " with z " + expressions[3].toString(e, debug) + " with thickness " + expressions[4].toString(e, debug) + " with height " + expressions[5].toString(e, debug);
    }
}