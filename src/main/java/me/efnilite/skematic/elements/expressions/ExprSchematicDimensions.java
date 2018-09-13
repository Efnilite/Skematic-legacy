package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.IOException;

public class ExprSchematicDimensions extends SimpleExpression<Vector> {

    private Expression<String> schem;

    static {
        Skript.registerExpression(ExprSchematicDimensions.class, Vector.class, ExpressionType.COMBINED, "[skematic] [the] dimension[s] of [the] [schem[atic]] %string%");
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        schem = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Skematic dimensions " + schem.toString(event, debug);
    }

    @Override
    @Nullable
    protected Vector[] get(Event event) {

        Vector dimension;
        File file = new File(schem.getSingle(event));
        try {
            dimension = FaweAPI.load(file).getClipboard().getDimensions();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        return new Vector[] { dimension };

    }
}

