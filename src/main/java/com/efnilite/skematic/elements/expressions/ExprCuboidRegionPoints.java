package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import org.bukkit.event.Event;

@Name("Selection points")
@Description("Gets the minimal or maximal points of a player's selection")
@Examples("set {_point} to the maximum point of player's selection")
@Since("1.0.0")
@Patterns({"[the] (1¦min|2¦max)[imum] (coord[inate]|point)[s] of %cuboidregions%",
            "%cuboidregions%'[s] (1¦min|2¦max)[imum] (coord[inate]|point)[s]"})
@Return(Vector.class)
@Single
public class ExprCuboidRegionPoints extends SkematicExpression<Vector> {

    static {
        Skript.registerExpression(ExprCuboidRegionPoints.class, Vector.class, ExpressionType.PROPERTY, "[the] (1¦min|2¦max)[imum] (coord[inate]|point)[s] of %cuboidregions%",
                "%cuboidregions%'[s] (1¦min|2¦max)[imum] (coord[inate]|point)[s]");
    }

    @Override
    protected Vector[] get(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        Vector vector = null;

        if (cuboid == null) {
            return null;
        }

        switch (mark) {
            case 1:
                vector = cuboid.getMinimumPoint();
                break;
            case 2:
                vector = cuboid.getMinimumPoint();
                break;
        }
        return new Vector[] { vector };
    }

}
