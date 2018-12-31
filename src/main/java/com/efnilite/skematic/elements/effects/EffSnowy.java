package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Snowify")
@Description("Place snow at a location - Let it go.")
@Examples("snowify 1, 3, 19 in \"world\" within a radius of 10")
public class EffSnowy extends SkematicEffect {

    static {
        Skript.registerEffect(EffSnowy.class, "(sim[ulate] snow at|place snow at|snowify) %direction% %location% (in|within) [a] radius [of] %number%");
    }

    @Override
    protected void execute(Event e) {
        Location location = Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1].getSingle(e)).getSingle(e);
        Number radius = (Number) expressions[2].getSingle(e);

        if (location == null || radius == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.simulateSnow(new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "snowify " + expressions[0].toString(e, debug) + " with radius " + expressions[1].toString(e, debug);
    }
}
