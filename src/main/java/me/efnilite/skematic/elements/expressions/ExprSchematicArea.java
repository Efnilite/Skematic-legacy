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

public class ExprSchematicArea extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprSchematicArea.class, Number.class, ExpressionType.COMBINED, "[skematic] (1¦width|2¦height|3¦lenght) of [the] [schem[atic]] %string%");
    }

    private Expression<String> schem;
    private int marker;

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
        return "schematic sizes of " + schem.toString(event, debug);
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
        switch (marker) {
            case 1:
                result = size.getY();
                break;
            case 2:
                result = size.getX();
                break;
            case 3:
                result = size.getZ();
                break;
            case 4:
                result = (size.getZ() * size.getX());
                break;
        } return new Number[] { result };
    }
}