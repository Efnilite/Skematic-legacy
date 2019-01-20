package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.sk89q.worldedit.regions.CuboidRegion;

@Name("Selection area")
@Description("Get the region area of a player's selection.")
@Since("2.0")
public class ExprCuboidRegionArea extends SimplePropertyExpression<CuboidRegion, Number> {

    static {
        register(ExprCuboidRegionArea.class, Number.class, "area[s]", "cuboidregions");
    }

    @Override
    public Number convert(CuboidRegion cuboid) {
        return cuboid.getArea();
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    protected String getPropertyName() {
        return "region area";
    }

}