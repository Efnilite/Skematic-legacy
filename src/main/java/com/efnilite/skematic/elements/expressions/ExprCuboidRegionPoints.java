package com.efnilite.skematic.elements.expressions;

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
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Selection points")
@Description("Gets the minimal or maximal points in a cuboidregion")
@Examples("set {_point} to the maximum point of player's selection")
@Since("1.5")
public class ExprCuboidRegionPoints extends SimpleExpression<BlockVector3> {

    private int mark;
    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerExpression(ExprCuboidRegionPoints.class, BlockVector3.class, ExpressionType.PROPERTY,
                "[the] [(skematic|fawe)] (1¦min|2¦max)[imum] (coord[inate]|point)[s] of %cuboidregions%",
                "%cuboidregions%'[s] [(skematic|fawe)] (1¦min|2¦max)[imum] (coord[inate]|point)[s]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];

        return true;
    }

    @Override
    protected BlockVector3[] get(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        BlockVector3 vector;

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
            default:
                vector = null;
                break;
        }
        return new BlockVector3[] { vector };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends BlockVector3> getReturnType() {
        return BlockVector3.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get min or max of " + cuboid.toString(e, debug);
    }
}
