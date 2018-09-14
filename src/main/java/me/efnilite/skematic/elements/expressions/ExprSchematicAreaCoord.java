package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExprSchematicAreaCoord extends SimpleExpression<Number> {

    private Expression<String> schem;
    private int marker;

    static {
        Skript.registerExpression(ExprSchematicAreaCoord.class, Number.class, ExpressionType.COMBINED, "[skematic] [the] (1¦x|2¦y|3¦z)(-| )coord[inate] of [the] [schem[atic]] %string%");
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        marker = parser.mark;
        schem = (Expression<String>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Skematic sizes " + schem.toString(event, debug);
    }

    @Override
    @Nullable
    protected Number[] get(Event event) {
        Vector size;
        File file = new File(schem.getSingle(event));
        try {
            size = FaweAPI.load(file).getClipboard().getDimensions();
        } catch (FileNotFoundException exception) {
            Utilities.sendConsoleMessage("Schematic file '" + file + "' not found! Error:" + exception);
            return null;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
        Number result = null;
        if (marker == 1) {
            result = size.getY();
        } else if (marker == 2) {
            result = size.getX();
        } else if (marker == 3) {
            result = size.getZ();
        } else if (marker == 4) {
            result = (size.getZ() * size.getX());
        }
        return new Number[] { result };
    }
}

