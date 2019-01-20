package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Selection dimensions")
@Description("Get one of the region dimensions of a cuboidregion.")
@Since("1.5")
public class ExprCuboidRegionDimensions extends SimpleExpression<Number> {

    private int mark;
    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerExpression(ExprCuboidRegionDimensions.class, Number.class, ExpressionType.PROPERTY, "[the] [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width) of %cuboidregions%",
                "[the] %cuboidregions%'s [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width)");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        mark = parseResult.mark;

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null) {
            return null;
        }

        switch (mark) {
            case 1:
                return new Number[] { cuboid.getLength() };
            case 2:
                return new Number[] { cuboid.getHeight() };
            case 3:
                return new Number[] { cuboid.getWidth() };
            default:
                return new Number[] { 0 };
        }
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
    public String toString(Event e, boolean debug) {
        return "dimensions of " + cuboid.toString(e, debug);
    }
}