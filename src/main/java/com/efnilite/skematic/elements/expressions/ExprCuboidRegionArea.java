package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicPropertyExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.regions.CuboidRegion;

@Name("Selection area")
@Description("Get the region area of a player's selection.")
@Patterns({"area[s]", "cuboidregions"})
@Return(Number.class)
@PropertyExpression
public class ExprCuboidRegionArea extends SkematicPropertyExpression<CuboidRegion, Number> {

    @Override
    public Number convert(final CuboidRegion r) {

        if (r == null) {
            return null;
        }

        return r.getArea();
    }

    @Override
    protected String getPropertyName() {
        return "region area";
    }

}