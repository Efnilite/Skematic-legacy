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

@Name("Schematic size")
@Description("Gets the size of a schematic.")
@Examples("set {_size} to the size of skematic \"plugins/WorldEdit/skematic.schematic\"")
@Patterns({"[s(ch|k)em[atic]] dimensions", "strings"})
@Return(Vector.class)
@PropertyExpression
public class ExprSchematicSize extends SkematicPropertyExpression<String, Vector> {

    static {
        register(ExprSchematicSize.class, Vector.class, "[s(ch|k)em[atic]] dimensions", "strings");
    }

    @Override
    public Vector convert(final String f) {
        Vector d;
        try {
            d = FaweAPI.load(new File(f)).getClipboard().getDimensions();
        } catch (IOException e) {
            return null;
        }
        return d;
    }
}


