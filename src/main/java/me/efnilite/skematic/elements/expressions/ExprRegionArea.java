package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.regions.Region;

public class ExprRegionArea extends SimplePropertyExpression<FawePlayer, Number> {

    static {
        register(ExprRegionArea.class, Number.class, "selection area[s]", "faweplayers");
    }

    @Override
    public Number convert(final FawePlayer p) {
        return p.getSelection().getArea();
    }

    @Override
    protected String getPropertyName() {
        return "selection area";
    }

    @Override
    public Class<Number> getReturnType() {
        return Number.class;
    }
}