package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Last region")
@Description("Get the last created region.")
@Examples({"create a new cuboidregion from {_location} to {_location-2}",
        "set {_region} to the last created region"})
@Patterns("[the] last[ly] created [(skematic|fawe)] [(cuboid|we|wordedit)][ ]region")
@Return(CuboidRegion.class)
@Single
public class ExprLastCuboidRegion extends SkematicExpression<CuboidRegion> {

    @Override
    protected CuboidRegion[] get(Event e) {
        return ExprCuboidRegion.getLastCuboidRegion();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last region";
    }
}
