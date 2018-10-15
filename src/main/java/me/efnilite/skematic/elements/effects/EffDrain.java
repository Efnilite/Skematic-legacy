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

@Name("Drain liquids")
@Description("Drain all liquids at a certain location with a radius.")
@Examples("drain all liquids at 124, 32, 12 in \"world\" in a radius of 10")
@Since("1.0.1")
public class EffDrain extends Effect {

    static {
        Skript.registerEffect(EffDrain.class, "drain [all] [(skematic|fawe)] [liquid[s]] [at] %location% (in|within) [a] radius [of] %number%");
    }

    private Expression<World> world;
    private Expression<Location> position;
    private Expression<Number> radius;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position = (Expression<Location>) exprs[0];
        radius = (Expression<Number>) exprs[2];
        world = (Expression<World>) exprs[1];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "drain area " + position.toString(e, debug) + " with radius " + radius.toString(e, debug) + " in world " + world.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        Location loc = position.getSingle(e);
        EditSession session = FaweAPI.getEditSessionBuilder(world.getSingle(e)).autoQueue(true).build();
        session.drainArea(new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), (double) radius.getSingle(e));
    }

}
