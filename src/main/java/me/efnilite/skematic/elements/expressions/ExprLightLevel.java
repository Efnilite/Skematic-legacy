package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprLightLevel extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprLightLevel.class, Number.class, ExpressionType.PROPERTY, "[the] [block(-| )]]light of [the] [block] (at|of) %location%");
    }

    private Expression<World> world;
    private Expression<Location> location;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        world = (Expression<World>) exprs[1];
        location = (Expression<Location>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "light level of block at " + location.toString(e, debug) + " in world " + world.toString(e, debug);
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
        try {
            return new Number[] { FaweAPI.getWorld(world.toString()).getBlockLightLevel(new Vector(location.getSingle(e).getBlockX(), location.getSingle(e).getBlockY(), location.getSingle(e).getBlockZ()))};
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
