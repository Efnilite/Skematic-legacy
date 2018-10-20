package me.efnilite.skematic.elements.expressions;

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
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

@Name("Last region")
@Description("Get the last created region.")
@Examples({"create a new cuboidregion from {_location} to {_location-2}",
        "set {_region} to the last created region"})
@Since("1.1.0")
public class ExprLastCuboidRegion extends SimpleExpression<CuboidRegion> {

    static {
        Skript.registerExpression(ExprLastCuboidRegion.class, CuboidRegion.class, ExpressionType.SIMPLE,
                "[the] last[ly] created [(cuboid|we|wordedit)][ ]region");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        return true;
    }

    @Override
    protected CuboidRegion[] get(Event e) {
        return ExprCuboidRegion.getLastCuboidRegion();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the lastly created cuboidregion";
    }

    @Override
    public Class<? extends CuboidRegion> getReturnType() {
        return CuboidRegion.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}
