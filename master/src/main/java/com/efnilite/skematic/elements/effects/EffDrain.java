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
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Drain liquids")
@Description("Drain all liquids at a certain location with a radius.")
@Examples("drain all liquids at 124, 32, 12 in \"world\" in a radius of 10")
public class EffDrain extends SkematicEffect {

    static {
        Skript.registerEffect(EffDrain.class, "drain [all] [(skematic|fawe)] [liquid[s]] %direction% %location% (in|within) [a] radius [of] %number%");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void execute(Event e) {
        Location location = (Location) Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1]);;
        Number radius = (Number) expressions[2].getSingle(e);

        if (radius == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.drainArea(FaweTools.toVector(location), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "drain " + expressions[1].toString(e, debug) + " in radius " + expressions[1].toString(e, debug);
    }
}
