package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Snowify")
@Description("Place snow at a location - Let it go.")
@Examples("snowify 1, 3, 19 in \"world\" within a radius of 10")
@Since("1.0.0")
public class EffSnowy extends Effect {

    static {
        Skript.registerEffect(EffSnowy.class, "(sim[ulate] snow at|place snow at|snowify) %location% in %world% (in|within) [a] radius [of] %number%");
    }

    private Expression<Location> position;
    private Expression<Number> radius;
    private Expression<World> world;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position = (Expression<Location>) exprs[0];
        radius = (Expression<Number>) exprs[1];
        world = (Expression<World>) exprs[2];

        return true;
    }

    @Override
    protected void execute(Event e) {

        Location l = position.getSingle(e);
        World w = world.getSingle(e);
        Number r = radius.getSingle(e);

        if (l == null || w == null || r == null) {
            return;
        }

        EditSession s = FaweAPI.getEditSessionBuilder(w).autoQueue(true).build();
        s.simulateSnow(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()), (double) r);
        s.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "simulate snow at " + position.toString(e, debug) + " with radius " + radius.toString(e, debug) + " in world " + world.toString(e, debug);
    }
}
