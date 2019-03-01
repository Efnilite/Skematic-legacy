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

@Name("Shape - Cylinder")
@Description("Create a cylinder at a location")
@Examples("create a cylinder at {_location} with pattern grass, glass and stone and radius 1 and height 2")
@Since("2.0")
public class EffShapeCylinder extends Effect {

    private int mark;
    private Expression<Location> location;
    private Expression<ItemType> blocks;
    private Expression<Number> radius;
    private Expression<Number> height;

    static {
        Skript.registerEffect(EffShapeCylinder.class, "(make|create) [a] [new] [(1¦hollow)] cylinder at %location% (with|and) %itemtypes% [block[s]] (with|and) radius %number% (with|and) height %number%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        mark = parseResult.mark;
        location = (Expression<Location>) expressions[0];
        blocks = (Expression<ItemType>) expressions[1];
        radius = (Expression<Number>) expressions[2];
        height = (Expression<Number>) expressions[3];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location = this.location.getSingle(e);
        ItemType[] blocks = this.blocks.getArray(e);
        Number radius = this.radius.getSingle(e);
        Number height = this.height.getSingle(e);

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
        return "create cylinder at " + location.toString(e, debug) + " with blocks " + blocks.toString(e, debug) + " with radius " + radius.toString(e, debug) + " and height " + height.toString(e, debug);
    }
}
