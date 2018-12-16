package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.ExpressionType;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.Vector;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.IOException;

@Name("Schematic dimensions")
@Description("Gets one of the schematic dimensions (width, length or height)")
@Examples("set {_width} to the width of the skematic \"plugins/WorldEdit/skematic.schematic\"")
@Since("1.0.0")
@Patterns("[the] (1¦width|2¦height|3¦length|4¦floor[(-| )]size) of [the] s(ch|k)em[atic] %string%")
@Return(Number.class)
public class ExprSchematicArea extends SkematicExpression<Number> {

    static {
        Skript.registerExpression(ExprSchematicArea.class, Number.class, ExpressionType.COMBINED,
                "[the] (1¦width|2¦height|3¦length|4¦floor[(-| )]size) of [the] s(ch|k)em[atic] %string%");
    }

    @Override
    @Nullable
    protected Number[] get(Event e) {

        String s = (String) expressions[0].getSingle(e);

        if (s == null) {
            return null;
        }
        Vector size;
        try {
            size = FaweAPI.load(new File(s)).getClipboard().getDimensions();
        } catch (IOException exception) {
            return null;
        }
        double t = 0;
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
        }
        return new Number[]{ t };
    }
}