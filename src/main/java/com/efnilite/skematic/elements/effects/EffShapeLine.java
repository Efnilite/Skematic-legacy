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

@Name("Shape - Line")
@Description("Create a line between 2 locations")
@Examples("create a new hollow line from player's location to {_point 1} with quartz block and radius 2")
@Since("2.0")
public class EffShapeLine extends Effect {

    private int mark;
    private Expression<Location> location1;
    private Expression<Location> location2;
    private Expression<ItemType> blocks;
    private Expression<Number> radius;

    static {
        Skript.registerEffect(EffShapeLine.class, "(make|create) [a] [new] [(1¦hollow)] line from %location% to %location% (with|and) %itemtypes% [block[s]] (with|and) radius %number%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        mark = parseResult.mark;
        location1 = (Expression<Location>) expressions[0];
        location2 = (Expression<Location>) expressions[1];
        blocks = (Expression<ItemType>) expressions[2];
        radius = (Expression<Number>) expressions[3];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location1 = this.location1.getSingle(e);
        Location location2 = this.location2.getSingle(e);
        ItemType[] blocks = this.blocks.getArray(e);
        Number radius = this.radius.getSingle(e);

        boolean filled = true;

        if (blocks == null || radius == null) {
            return;
        }

        if (mark == 1) {
            filled = false;
        }

        EditSession session = FaweUtils.getEditSession(location1.getWorld());
        session.drawLine(FaweUtils.parsePattern(blocks), FaweUtils.toVector(location1), FaweUtils.toVector(location2), Math.round((long) radius), filled);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create a line between " + location1.toString(e, debug) + " and " + location2.toString(e, debug) + " with pattern " + blocks.toString(e, debug);
    }
}
