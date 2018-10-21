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
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.efnilite.skematic.Skematic;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Name("Schematic dimensions")
@Description("Gets one of the schematic dimensions (width, length or height)")
@Examples("set {_width} to the width of the skematic \"plugins/WorldEdit/skematic.schematic\"")
@Since("1.0.0")
public class ExprSchematicArea extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprSchematicArea.class, Number.class, ExpressionType.COMBINED,
                "[the] (1¦width|2¦height|3¦length|4¦floor[(-| )]size) of [the] s(ch|k)em[atic] %schematics%");
    }

    enum Dimension {
        WIDTH, HEIGHT, LENGTH, FLOORSIZE
    }

    private Expression<String> schematic;
    private Dimension dimension;


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        dimension = Dimension.values()[parser.mark - 1];

        schematic = (Expression<String>) exprs[0];

        return true;
    }

    @Override
    @Nullable
    protected Number[] get(Event e) {

        String s = schematic.getSingle(e);

        if (s == null || dimension == null) {
            return null;
        }

        Vector size;
        File file = new File(s);

        try {
            size = FaweAPI.load(file).getClipboard().getDimensions();
        } catch (FileNotFoundException exception) {
            Skematic.log("Schematic file '" + file + "' not found! Error:" + exception);
            return null;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

        Number t = null;

        switch (dimension) {
            case WIDTH:
                t = size.getY();
                break;
            case HEIGHT:
                t = size.getX();
                break;
            case LENGTH:
                t = size.getZ();
                break;
            case FLOORSIZE:
                t = (size.getZ() * size.getX());
                break;
        }
        return new Number[]{
                t
        };
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "schematic sizes of " + schematic.toString(event, debug);
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }
}