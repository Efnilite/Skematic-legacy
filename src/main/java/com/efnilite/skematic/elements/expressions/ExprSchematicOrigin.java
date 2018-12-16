package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.lang.SkematicPropertyExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.Vector;

import java.io.File;
import java.io.IOException;

@Name("Schematic origin")
@Description("Returns the origin location of a schematic (where it was copied and saved)")
@Examples("set {_origin} to the origin of \"plugins/WorldEdit/skematic.schematic\"")
@Patterns({"[s(ch|k)em[atic]] origin (location|area)", "strings"})
@Return(Vector.class)
@PropertyExpression
public class ExprSchematicOrigin extends SkematicPropertyExpression<String, Vector> {

    static {
        register(ExprSchematicOrigin.class, Vector.class, "[s(ch|k)em[atic]] origin (location|area)", "strings");
    }

    @Override
    public Vector convert(final String f) {
        try {
            return FaweAPI.load(new File(f)).getClipboard().getOrigin();
        } catch (IOException e) {
            return null;
        }
    }
}
