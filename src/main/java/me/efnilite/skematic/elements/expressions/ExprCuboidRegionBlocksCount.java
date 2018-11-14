package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.Event;

public class ExprCuboidRegionBlocksCount extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocksCount.class, Number.class, ExpressionType.PROPERTY,
                "[skematic] (size|amount) of %material% in [region] %cuboidregions%",
                "%cuboidregions%'s (size|amount) of %material%");
    }

    private Expression<ItemType> material;
    private Expression<CuboidRegion> region;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        material = (Expression<ItemType>) exprs[0];
        region = (Expression<CuboidRegion>) exprs[1];

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        ItemType m = material.getSingle(e);
        CuboidRegion r = region.getSingle(e);

        if (r == null || m == null) {
            return null;
        }

        Vector pos1 = r.getPos1();
        Vector pos2 = r.getPos2();

        World w = Bukkit.getServer().getWorld(r.getWorld().getName());

        int blocks = 0;
        for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
            for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
                for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                    Material block = w.getBlockAt(x, y, z).getType();
                    if (block == m.getRandom().getType()) {
                        blocks++;
                    }
                }
            }
        }
        return new Number[] { blocks };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "amount of " + material.getSingle(e) + " in " +  region.toString(e, debug);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
