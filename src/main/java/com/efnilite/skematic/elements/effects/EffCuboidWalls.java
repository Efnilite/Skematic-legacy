package com.efnilite.skematic.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Shape - Line")
@Description("Create a line between 2 locations")
@Patterns("(make|create) [a] [new] [(1¦hollow)] line from %direction% %location% to %direction% %location% (with|and) [(itemtypes[s]|block[s]|pattern)] %itemtypes% (with|and) radius %number%")
public class EffShapeLine extends SkematicEffect {

    @Override
    protected void execute(Event e) {
        Location location1 = (Location) Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1]);
        Location location2 = (Location) Direction.combine((Expression<Direction>) expressions[2], (Expression<Location>) expressions[3]);
        ItemType[] blocks = (ItemType[]) expressions[4].getAll(e);
        Number radius = (Number) expressions[5].getSingle(e);

        boolean filled = true;

        if (blocks == null || radius == null) {
            return;
        }

        if (mark == 1) {
            filled = false;
        }

        EditSession session = FaweTools.getEditSession(location1.getWorld());
        session.drawLine(FaweTools.parsePattern(blocks), FaweTools.toVector(location1), FaweTools.toVector(location2), Math.round((long) radius), filled);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create a line between " + expressions[1].toString(e, debug) + " and " + expressions[3].toString(e, debug) + " with pattern " + expressions[4].toString(e, debug);
    }
}
