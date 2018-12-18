package com.efnilite.skematic.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Drain liquids")
@Description("Drain all liquids at a certain location with a radius.")
@Examples("drain all liquids at 124, 32, 12 in \"world\" in a radius of 10")
@Patterns("drain [all] [(skematic|fawe)] [liquid[s]] [at] %location% (in|within) [a] radius [of] %number%")
public class EffDrain extends SkematicEffect {

    @Override
    protected void execute(Event e) {
        Location location = (Location) expressions[0].getSingle(e);
        Number radius = (Number) expressions[1].getSingle(e);

        if (radius == null || location == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.drainArea(FaweTools.toVector(location), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "drain " + expressions[0].toString(e, debug) + " in radius " + expressions[1].toString(e, debug);
    }
}
