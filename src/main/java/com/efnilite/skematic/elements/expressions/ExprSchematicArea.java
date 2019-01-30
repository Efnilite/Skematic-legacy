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
import com.efnilite.skematic.objects.Schematic;
import com.efnilite.skematic.objects.SchematicLoader;
import com.sk89q.worldedit.Vector;
import org.bukkit.event.Event;

import java.io.File;
import java.nio.file.Paths;

@Name("Schematic dimensions")
@Description("Gets one of the schematic dimensions (width, length or height)")
@Examples("set {_width} to the width of the skematic \"plugins/Transport/Berm.schematic\"")
@Since("1.0")
public class ExprSchematicArea extends SimpleExpression<Number> {

    private int mark;
    private Expression<?> schematic;

    static {
        Skript.registerExpression(ExprSchematicArea.class, Number.class, ExpressionType.PROPERTY, "[the] [(skematic|fawe)] (1¦width|2¦height|3¦length|4¦floor[(-| )]size) of [the] s(ch|k)em[atic] %schematics%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = expressions[0];

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        Schematic schematic;
        if (this.schematic.getSingle(e) instanceof String) {
            String file = (String) this.schematic.getSingle(e);
            if (SchematicLoader.getSchematics().containsKey(file)) {
                schematic = SchematicLoader.get(file);
            } else if (Paths.get(file).toFile().exists()) {
                schematic = new Schematic(new File(file));
            } else {
                Skript.error("Schematic " + file + " doesn't exist!");
                return null;
            }
        } else if (this.schematic.getSingle(e) instanceof Schematic) {
            schematic = (Schematic) this.schematic.getSingle(e);
        } else {
            return null;
        }

        Vector size = schematic.getClipboard().getDimensions();

        double t;
        switch (mark) {
            case 1:
                t = size.getY();
                break;
            case 2:
                t = size.getX();
                break;
            case 3:
                t = size.getZ();
                break;
            case 4:
                t = (size.getZ() * size.getX());
                break;
            default:
                t = 0;
                break;
        }
        return new Number[] { t };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "areas of " + schematic.toString(e, debug);
    }
}