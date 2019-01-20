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

@Name("Snowify")
@Description("Place snow at a location - Let it go.")
@Examples("snowify 1, 3, 19 in \"world\" within a radius of 10")
@Since("1.0")
public class EffSnowy extends Effect {

    private Expression<Location> location;
    private Expression<Number> radius;

    static {
        Skript.registerEffect(EffSnowy.class, "(sim[ulate]|place) snow at %location% (in|within) [a] radius [of] %number%");
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

        if (location == null || radius == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.simulateSnow(FaweUtils.toVector(location), (double) radius);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "snowify " + location.toString(e, debug) + " with radius " + radius.toString(e, debug);
    }
}
