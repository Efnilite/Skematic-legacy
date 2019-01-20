package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.efnilite.skematic.objects.Schematic;
import com.sk89q.worldedit.Vector;

@Name("Schematic origin")
@Description("Returns the origin location of a schematic (where it was copied and saved)")
@Examples("set {_origin} to the origin of \"plugins/WorldEdit/schematics/Windows.schematic\"")
@Since("1.0")
public class ExprSchematicOrigin extends SimplePropertyExpression<Schematic, Vector> {

    static {
        register(ExprSchematicOrigin.class, Vector.class, "[(skematic|fawe)] [s(ch|k)em[atic]] origin (location|area)", "schematics");
    }

    @Override
    public Vector convert(Schematic schematic) {
        return schematic.getClipboard().getOrigin();
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    protected String getPropertyName() {
        return "origin";
    }
}
