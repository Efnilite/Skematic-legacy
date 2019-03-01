package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
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

@Name("Greenify")
@Description("Greenify an area - Turns it into grass.")
@Examples("greenify 2, 3, 4 in \"world\" within a radius of 20")
@Since("1.0")
public class EffGreen extends Effect {

    private Expression<Location> location;
    private Expression<Number> radius;

    static {
        Skript.registerEffect(EffGreen.class, "(green|grass)[ify] at %location% (in|within) [a] radius [of] %number%");
    }

    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        location = (Expression<Location>) expressions[0];
        radius = (Expression<Number>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location = this.location.getSingle(e);
        Number radius = this.radius.getSingle(e);

        if (radius == null || location == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.green(FaweUtils.toVector(location), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "grassify " + location.toString(e, debug) + " with radius " + radius.toString(e, debug);
    }
}
