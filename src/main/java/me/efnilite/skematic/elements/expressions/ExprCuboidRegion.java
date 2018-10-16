package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Custom region")
@Description("Create a virtual region (for saving schematics, etc.)")
@Examples("set {region} to a new region between location 0, 2, 3 in \"world\" and location 4, 3, 7 in \"world\"")
@Since("1.1.0")
public class ExprCuboidRegion extends SimpleExpression<CuboidRegion> {

    static {
        Skript.registerExpression(ExprCuboidRegion.class, CuboidRegion.class, ExpressionType.SIMPLE,
                "[(the|[a] new)] (worldedit|we)[ ]region (within|between) %location% and %location%");
    }

    private Expression<Location> position1;
    private Expression<Location> position2;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position1 = (Expression<Location>) exprs[0];
        position2 = (Expression<Location>) exprs[1];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "the new region between " + position1.toString(e, debug) + " and " + position2.toString(e, debug);
    }

    @Override
    public Class<? extends CuboidRegion> getReturnType() {
        return CuboidRegion.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    protected CuboidRegion[] get(Event e) {

        Location p1 = position1.getSingle(e);
        Location p2 = position2.getSingle(e);

        if (p1 == null || p2 == null) {
            return null;
        }

        return new CuboidRegion[] { new CuboidRegion(BukkitUtil.getLocalWorld(p1.getWorld()),
                new Vector(p1.getBlockX(), p1.getBlockY(), p1.getBlockZ()),
                new Vector(p2.getBlockX(), p2.getBlockY(), p2.getBlockZ()))
        };
    }

}
