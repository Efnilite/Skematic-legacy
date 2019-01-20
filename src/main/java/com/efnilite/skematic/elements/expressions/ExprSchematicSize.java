package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.efnilite.skematic.objects.Schematic;
import com.sk89q.worldedit.Vector;

@Name("Schematic size")
@Description("Gets the size of a schematic.")
@Examples("set {_size} to the size of skematic \"plugins/WorldEdit/skematic.schematic\"")
@Since("1.0")
public class ExprSchematicSize extends SimplePropertyExpression<Schematic, Vector> {

    static {
        register(ExprSchematicSize.class, Vector.class, "[(skematic|fawe)] [s(ch|k)em[atic]] dimensions", "schematics");
    }

    @Override
    public Vector convert(Schematic schematic) {
        return schematic.getClipboard().getDimensions();
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    protected String getPropertyName() {
        return "dimensions";
    }
}


