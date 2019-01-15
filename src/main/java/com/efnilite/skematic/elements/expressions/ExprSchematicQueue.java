package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.queue.SchematicData;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.event.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Name("Schematic queue")
@Description("All the items in the current queue.")
@Return(File.class)
public class ExprSchematicQueue extends SkematicExpression<File> {

    static {
        Skript.registerExpression(ExprSchematicQueue.class, File.class, ExpressionType.PROPERTY, "[the] [current] s(ch|k)em[atic] queue");
    }

    @Override
    protected File[] get(Event e) {
        List<File> list = new ArrayList<>();
        List<SchematicData> queue = SchematicQueue.getQueue();
        while (queue.iterator().hasNext()) {
            list.add(queue.iterator().next().getSchematic());
        }
        return (File[]) list.toArray();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last schematic";
    }
}
