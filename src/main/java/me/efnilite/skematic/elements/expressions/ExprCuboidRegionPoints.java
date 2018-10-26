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
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Selection points")
@Description("Gets the minimal or maximal points of a player's selection")
@Examples("set {_point} to the maximum point of player's selection")
@Since("1.0.0")
public class ExprCuboidRegionPoints extends SimpleExpression<Vector> {

    static {
        Skript.registerExpression(ExprCuboidRegionPoints.class, Vector.class, ExpressionType.PROPERTY, "[the] (1¦min|2¦max)[imum] (coord[inate]|point)[s] of %cuboidregions%",
                "%cuboidregions%'[s] (1¦min|2¦max)[imum] (coord[inate]|point)[s]");
    }

    enum Point {
        MIN, MAX
    }

    private Expression<CuboidRegion> region;
    private Point point;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        point = Point.values()[parseResult.mark - 1];

        region = (Expression<CuboidRegion>) exprs[0];

        return true;
    }

    @Override
    protected Vector[] get(Event e) {
        Vector r = null;
        switch (point) {
            case MAX:
                r = region.getSingle(e).getMinimumPoint();
                break;
            case MIN:
                r = region.getSingle(e).getMaximumPoint();
                break;
        }
        return new Vector[] {
                r
        };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "points of " + region.toString(e, debug) ;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }


}
