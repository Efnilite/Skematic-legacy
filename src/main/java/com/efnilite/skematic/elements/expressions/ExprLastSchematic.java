package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.event.Event;

import java.io.File;

@Name("Last schematic")
@Description("The last schematic added to the queue.")
@Return(File.class)
@Single
public class ExprLastSchematic extends SkematicExpression<File> {

    static {
        Skript.registerExpression(ExprLastSchematic.class, File.class, ExpressionType.PROPERTY, "[the] last s(k|ch)em[atic]");
    }

    @Override
    protected File[] get(Event e) {
        return new File[] { SchematicQueue.getLastQueued().getSchematic() };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last schematic";
    }
}
