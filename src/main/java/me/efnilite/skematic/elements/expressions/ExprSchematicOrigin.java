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

@Name("Schematic origin")
@Description("Returns the origin location of a schematic (where it was copied and saved)")
@Examples("set {_origin} to the origin of skematic \"plugins/WorldEdit/skematic.schematic\"")
@Since("1.0.0")
public class ExprSchematicOrigin extends SimpleExpression<Vector> {

    static {
        Skript.registerExpression(ExprSchematicOrigin.class, Vector.class, ExpressionType.COMBINED, "[skematic] [the] origin [area] of [s(ch|k)em[atic]] %string%");
    }

    private Expression<String> schem;

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
        return "schematic origin of " + schem.toString(event, debug);
    }

    @Override
    @Nullable
    protected Vector[] get(Event event) {

        Vector origin;
        File file = new File(schem.getSingle(event));
        try {
            origin = FaweAPI.load(file).getClipboard().getOrigin();
        } catch (FileNotFoundException exception) {
            Skematic.log("Schematic file '" + file + "' not found! Error:" + exception);
            return null;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        } return new Vector[] { origin };
    }
}
