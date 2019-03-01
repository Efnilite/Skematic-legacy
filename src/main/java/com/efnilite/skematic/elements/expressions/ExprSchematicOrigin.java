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

@Name("Schematic origin")
@Description("Returns the origin location of a schematic (where it was copied and saved)")
@Examples("set {_origin} to the origin of \"plugins/WorldEdit/schematics/Windows.schematic\"")
@Since("1.0")
public class ExprSchematicOrigin extends SimpleExpression<Vector> {

    private Expression<?> schematic;

    static {
        Skript.registerExpression(ExprSchematicOrigin.class, Vector.class, ExpressionType.PROPERTY,
                "[(skematic|fawe)] [s(ch|k)em[atic]] origin (location|area) of %schematics/strings%",
                "%schematics/strings%'[s] [(skematic|fawe)] [s(ch|k)em[atic]] origin (location|area)");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = expressions[0];

        return true;
    }

    @Override
    protected Vector[] get(Event e) {
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

        return new Vector[] { schematic.getClipboard().getOrigin() };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "origin of " + schematic.toString(e, debug);
    }
}
