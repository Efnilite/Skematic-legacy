package me.efnilite.skematic.hooks.worldguard.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprVolumeRegion extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprVolumeRegion.class, Number.class, ExpressionType.COMBINED, "[the] (volume|dimensions) of [the] region %string% in [world] %string%");
    }

    private Expression<World> world;
    private Expression<String> name;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];

        return true;
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
        if (WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)) != null && WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)).getRegion(name.toString()).volume() != 0) volume = WorldGuard.getWorldGuard().getRegionManager(Bukkit.getServer().getWorld("Lobby-1")).getRegion("hi").volume();
        else Utilities.error("Could not get the volume of region " + name.toString() + " in world " + world.toString() + ". Maybe the world is blacklisted?", null, false);
        return new Number[] { volume };
    }
}
