package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Shape - Line")
@Description("Create a line between 2 locations")
public class EffShapeLine extends SkematicEffect {

    static {
        Skript.registerEffect(EffShapeLine.class, "(make|create) [a] [new] [(1¦hollow)] line from %location% to %location% (with|and) [(itemtypes[s]|block[s]|pattern)] %itemtypes% (with|and) radius %number%");
    }

    @Override
    protected void execute(Event e) {
        Location location1 = (Location) expressions[0].getSingle(e);
        Location location2 = (Location) expressions[1].getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[2].getAll(e);
        Number radius = (Number) expressions[3].getSingle(e);

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
        return "create a line between " + expressions[1].toString(e, debug) + " and " + expressions[3].toString(e, debug) + " with pattern " + expressions[4].toString(e, debug);
    }
}
