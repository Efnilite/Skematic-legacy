package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Shape - Hollow cylinder")
@Description("Create a hollow cylinder at a location.")
@Examples("make a new hollow cylinder at player's location with blocks grass and dirt with x radius 2 and z radius 2 and thickness 1 and height 4")
@Since("2.0")
public class EffShapeHollowCylinder extends Effect {

    private Expression<Location> location;
    private Expression<ItemType> blocks;
    private Expression<Number> x;
    private Expression<Number> z;
    private Expression<Number> thickness;
    private Expression<Number> height;

    static {
        Skript.registerEffect(EffShapeHollowCylinder.class, "(make|create) [a] [new] hollow cylinder at %location% (with|and) %itemtypes% [block[s]] (with|and) x[(-| )]radius %number% (with|and) z[(-| )]radius %number% (with|and) thickness %number% (with|and) height %number%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        location = (Expression<Location>) expressions[0];
        blocks = (Expression<ItemType>) expressions[1];
        x = (Expression<Number>) expressions[2];
        z = (Expression<Number>) expressions[3];
        thickness = (Expression<Number>) expressions[4];
        height = (Expression<Number>) expressions[5];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location = this.location.getSingle(e);
        ItemType[] blocks = this.blocks.getArray(e);
        Number x = this.x.getSingle(e);
        Number z = this.z.getSingle(e);
        Number thickness = this.thickness.getSingle(e);
        Number height = this.height.getSingle(e);

        if (blocks == null || x == null || z == null || thickness == null || height == null | location == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.makeHollowCylinder(FaweUtils.toVector(location), FaweUtils.parsePattern(blocks), (Double) x, (Double) z, Math.round((long) height), (Double) thickness);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create hollow cylinder at " + location.toString(e, debug) + " with blocks " + blocks.toString(e, debug) + " with x " + x.toString(e, debug) + " with z " + z.toString(e, debug) + " with thickness " + thickness.toString(e, debug) + " with height " + height.toString(e, debug);
    }
}