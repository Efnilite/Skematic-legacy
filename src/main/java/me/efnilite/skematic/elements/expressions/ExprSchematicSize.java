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
public class ExprSchematicSize extends SimplePropertyExpression<File, Vector> {

    static {
        register(me.efnilite.skematic.elements.expressions.ExprSchematicOrigin.class, Vector.class, "(dimensions|size)", "schematics");
    }

    @Override
    public Vector convert(final File f) {

        Vector d;

        try {
            d = FaweAPI.load(f).getClipboard().getDimensions();
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


