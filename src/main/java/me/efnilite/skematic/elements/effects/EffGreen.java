package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
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

public class EffGreen extends Effect {

    static {
        Skript.registerEffect(EffGreen.class, "(green|grass)[ify] %location% in %world% (in|within) [a] radius [of] %number%");
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
        return "greenify " + position.toString(e, debug) + " with radius " + radius.toString(e, debug) + " in world " + world.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        Location loc = position.getSingle(e);
        EditSession session = FaweAPI.getEditSessionBuilder(world.getSingle(e)).autoQueue(true).build();
        session.green(new Vector(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), (double) radius.getSingle(e));
    }

}
