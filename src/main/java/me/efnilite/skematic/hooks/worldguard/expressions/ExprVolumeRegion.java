package me.efnilite.skematic.hooks.worldguard.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprVolumeRegion extends SimpleExpression<Number> {

    private Expression<World> world;
    private Expression<String> name;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];

        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the volume of the region " + name.toString(e, debug) + " in world " + world.toString(e, debug);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected Number[] get(Event e) {
        Number volume = null;
        try {
            volume = WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)).getRegion(name.getSingle(e)).volume();
        } catch (NullPointerException exception) {
            Utilities.error("Could not get the volume of region " + name.toString() + " in world " + world.toString() + ". Maybe the world is blacklisted?", exception, false);;
        }
        return new Number[] { volume };
    }
}
