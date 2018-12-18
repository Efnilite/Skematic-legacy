package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Selection dimensions")
@Description("Get one of the region dimensions of a player's selection.")
@Patterns({"[the] [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width) of %cuboidregions%",
        "[the] %cuboidregions%'s [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width)"})
@Return(Number.class)
@Single
public class ExprCuboidRegionDimensions extends SkematicExpression<Number> {

    @Override
    protected Number[] get(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        if (cuboid == null) {
            return null;
        }
        double t = 0;
        switch (mark) {
            case 1:
                t = cuboid.getLength();
                break;
            case 2:
                t = cuboid.getHeight();
                break;
            case 3:
                t = cuboid.getWidth();
                break;
        }
        return new Number[] { t };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get height of " + expressions[0].toString(e, debug);
    }
}