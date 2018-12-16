package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Selection dimensions")
@Description("Get one of the region dimensions of a player's selection.")
@Examples("set {area} to the length of player's selection")
@Patterns({"[the] [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width) of %cuboidregions%",
        "[the] %cuboidregions%'s [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width)"})
@Return(Number.class)
@Single
public class ExprCuboidRegionDimensions extends SkematicExpression<Number> {

    static {
        Skript.registerExpression(ExprCuboidRegionDimensions.class, Number.class, ExpressionType.PROPERTY,
                "[the] [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width) of %cuboidregions%",
                "[the] %cuboidregions%'s [(skematic|fawe)] (cuboid|we|worldedit)[ ]region (1¦length|2¦height|3¦width)");
    }

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
}