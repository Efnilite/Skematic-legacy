package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Snowify")
@Description("Place snow at a location - Let it go.")
@Examples("snowify 1, 3, 19 in \"world\" within a radius of 10")
@Patterns("(sim[ulate] snow at|place snow at|snowify) %location% (in|within) [a] radius [of] %number%")
public class EffSnowy extends SkematicEffect {

    static {
        Skript.registerEffect(EffSnowy.class, "(sim[ulate] snow at|place snow at|snowify) %location% (in|within) [a] radius [of] %number%");
    }

    @Override
    protected void execute(Event e) {
        Location location = (Location) expressions[0].getSingle(e);
        Number radius = (Number) expressions[1].getSingle(e);

        if (location == null || radius == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.simulateSnow(new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()), (double) radius);
        session.flushQueue();
    }
}
