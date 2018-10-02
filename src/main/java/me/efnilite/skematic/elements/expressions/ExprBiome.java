package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.world.biome.BaseBiome;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprBiome extends SimpleExpression<BaseBiome> {

    static {
        Skript.registerExpression(ExprBiome.class, BaseBiome.class, ExpressionType.PROPERTY, "[the] biome (of|at) %integer% to %integer% in %world%");
    }

    private Expression<World> world;
    private Expression<Integer> x;
    private Expression<Integer> z;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        world = (Expression<World>) exprs[0];
        x = (Expression<Integer>) exprs[1];
        z = (Expression<Integer>) exprs[2];

        return true;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends BaseBiome> getReturnType() {
        return BaseBiome.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    protected BaseBiome[] get(Event e) {
        return new BaseBiome[] { FaweAPI.getWorld(world.toString()).getBiome(new Vector2D(x.getSingle(e),z.getSingle(e))) };
    }
}
