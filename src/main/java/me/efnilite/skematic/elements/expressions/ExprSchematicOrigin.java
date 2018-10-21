package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.hooks.regions.classes.Region;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import me.efnilite.skematic.Skematic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Name("Schematic origin")
@Description("Returns the origin location of a schematic (where it was copied and saved)")
@Examples("set {_origin} to the origin of \"plugins/WorldEdit/skematic.schematic\"")
@Since("1.0.0")
public class ExprSchematicOrigin extends SimplePropertyExpression<File, Vector> {

    static {
        register(ExprSchematicOrigin.class, Vector.class, "[s(ch|k)em[atic]] origin (location|area)", "schematics");
    }

    @Override
    public Vector convert(final File f) {

        try {
            return FaweAPI.load(f).getClipboard().getOrigin();
        } catch (FileNotFoundException exception) {
            Skematic.log("Schematic file '" + f + "' not found! Error:" + exception);
            return null;
        } catch (IOException e) {
            return null;
        }
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
