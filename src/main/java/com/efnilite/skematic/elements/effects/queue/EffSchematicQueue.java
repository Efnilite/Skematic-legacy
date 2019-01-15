package com.efnilite.skematic.elements.effects.queue;

import ch.njol.skript.Skript;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.queue.SchematicData;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.event.Event;

import java.io.File;

public class EffSchematicQueue extends SkematicEffect {

    static {
        Skript.registerEffect(EffSchematicQueue.class, "add %skematics% to schematic queue");
    }

    @Override
    protected void execute(Event e) {
        File schematic = (File) expressions[0].getSingle(e);

        if (schematic == null) {
            return;
        }

        SchematicQueue.addToQueue(new SchematicData(schematic));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "add " + expressions[0].toString(e, debug) + " to queue";
    }
}
