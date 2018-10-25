package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.efnilite.skematic.Skematic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

@Name("Schematic size")
@Description("Gets the size of a schematic.")
@Examples("set {_size} to the size of skematic \"plugins/WorldEdit/skematic.schematic\"")
@Since("1.0.0")
public class ExprSchematicSize extends SimplePropertyExpression<String, Vector> {

    static {
        register(ExprSchematicSize.class, Vector.class, "[s(ch|k)em[atic]] dimensions", "strings");
    }

    @Override
    public Vector convert(final String f) {

        Vector d;

        try {
            d = FaweAPI.load(new File(f)).getClipboard().getDimensions();
        } catch (FileNotFoundException exception) {
            Skematic.log("File " + f + " was not found!", Level.SEVERE);
            return null;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }

        if (d == null) {
            return null;
        }

        return d;
    }

    @Override
    protected String getPropertyName() {
        return "origin location";
    }

    @Override
    public Class<Vector> getReturnType() {
        return Vector.class;
    }
}


