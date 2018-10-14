package me.efnilite.skematic.hooks.worldguard.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.avaje.ebean.LogLevel;
import com.sk89q.worldguard.protection.managers.RegionManager;
import me.efnilite.skematic.Skematic;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.logging.Level;

public class ExprVolumeRegion extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprVolumeRegion.class, Number.class, ExpressionType.COMBINED, "[the] [(skematic|fawe)] (volume|dimensions) of [the] region %string% in %world%");
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
        RegionManager regionManager = WorldGuard.getWorldGuard().getRegionManager(Bukkit.getServer().getWorld(world.getSingle(e).toString()));
        if (regionManager != null) {
            return new Number[] { regionManager.getRegion(name.getSingle(e)).volume() };
        } else {
            return null;
        }
    }
}
