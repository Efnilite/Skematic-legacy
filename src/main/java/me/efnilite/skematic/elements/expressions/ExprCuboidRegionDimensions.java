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
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.event.Event;

@Name("Selection dimensions")
@Description("Get one of the region dimensions of a player's selection.")
@Examples("set {area} to the length of player's selection")
@Since("1.0.0")
public class ExprCuboidRegionDimensions extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprCuboidRegionDimensions.class, Number.class, ExpressionType.PROPERTY,
                "[the] [(skematic|fawe)] (1¦length|2¦height|3¦width) of %cuboidregions%",
                "[the] %cuboidregions%'s [(skematic|fawe)] (1¦length|2¦height|3¦width)");
    }

    enum Dimension {
        LONG, HIGH, WIDE
    }

    private Expression<CuboidRegion> region;
    private Dimension dimension;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        dimension = Dimension.values()[parser.mark - 1];

        region = (Expression<CuboidRegion>) exprs[0];

        return true;
    }

    @Override
    protected Number[] get(Event e) {

        Region r = region.getSingle(e);

        if (r == null) {
            return null;
        }

        Number t = null;
        switch (dimension) {
            case LONG:
                t = region.getSingle(e).getLength();
                break;
            case HIGH:
                t = region.getSingle(e).getHeight();
                break;
            case WIDE:
                t = region.getSingle(e).getWidth();
                break;
        }
        return new Number[] {
                t
        };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "dimensions of " + region.toString(e, debug);
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