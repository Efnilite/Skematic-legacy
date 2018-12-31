package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Greenify")
@Description("Greenify an area - Turns it into grass.")
@Examples("greenify 2, 3, 4 in \"world\" within a radius of 20")
public class EffGreen extends SkematicEffect {

    static {
        Skript.registerEffect(EffGreen.class, "(green|grass)[ify] at %location% (in|within) [a] radius [of] %number% (1¦[(with|and)] only [normal] dirt)");
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void execute(Event e) {
        Location location = (Location) expressions[0].getSingle(e);
        Number radius = (Number) expressions[1].getSingle(e);
        boolean dirt = false;

        if (radius == null) {
            return;
        }

        if (mark == 1) {
            dirt = true;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.green(FaweTools.toVector(location), (double) radius, dirt);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grassify " + expressions[0].toString(e, debug) + " with radius " + expressions[1].toString(e, debug);
    }
}
