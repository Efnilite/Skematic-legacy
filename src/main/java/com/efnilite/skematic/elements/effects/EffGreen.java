package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
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

@Name("Greenify")
@Description("Greenify an area - Turns it into grass.")
@Examples("greenify 2, 3, 4 in \"world\" within a radius of 20")
public class EffGreen extends SkematicEffect {

    static {
        Skript.registerEffect(EffGreen.class, "(green|grass)[ify] %direction% %location% (in|within) [a] radius [of] %number%");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void execute(Event e) {
        Location location = Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1]).getSingle(e);
        Number radius = (Number) expressions[2].getSingle(e);

        if (radius == null || location == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.green(FaweUtils.toVector(location), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grassify " + expressions[0].toString(e, debug) + " with radius " + expressions[1].toString(e, debug);
    }
}
