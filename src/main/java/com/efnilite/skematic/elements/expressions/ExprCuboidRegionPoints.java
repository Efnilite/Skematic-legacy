package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Selection points")
@Description("Gets the minimal or maximal points of a player's selection")
@Examples("set {_point} to the maximum point of player's selection")
@Patterns({"[the] [(skematic|fawe)] (1¦min|2¦max)[imum] (coord[inate]|point)[s] of %cuboidregions%",
            "%cuboidregions%'[s] [(skematic|fawe)] (1¦min|2¦max)[imum] (coord[inate]|point)[s]"})
@Return(Vector.class)
@Single
public class ExprCuboidRegionPoints extends SkematicExpression<Vector> {

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

    @Override
    public String toString(Event e, boolean debug) {
        return "get min or max of " + expressions[0].toString(e, debug);
    }
}
