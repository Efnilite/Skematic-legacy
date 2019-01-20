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

@Name("Drain liquids")
@Description("Drain all liquids at a certain location with a radius.")
@Examples("drain all liquids at 124, 32, 12 in \"world\" in a radius of 10")
@Since("2.0")
public class EffDrain extends Effect {

    private Expression<Location> location;
    private Expression<Number> radius;

    static {
        Skript.registerEffect(EffDrain.class, "drain [all] [(skematic|fawe)] [liquid[s]] at %location% (in|within) [a] radius [of] %number%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        location = (Expression<Location>) expressions[0];
        radius = (Expression<Number>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location = this.location.getSingle(e);
        Number radius = this.radius.getSingle(e);

        if (radius == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.drainArea(FaweUtils.toVector(location), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "drain " + location.toString(e, debug) + " in radius " + radius.toString(e, debug);
    }
}
