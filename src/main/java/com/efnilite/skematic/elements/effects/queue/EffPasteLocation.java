package com.efnilite.skematic.elements.effects.queue;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.io.File;

@Name("Ignore pasteoptions")
public class EffPasteLocation extends SkematicEffect {

    static {
        Skript.registerEffect(EffPasteLocation.class,
                "set (location|placement) of [[in-]queue[d]] %skematics% to %location%", "set %skematics%'[s] (location|placement) to %location%");
    }

    @Override
    protected void execute(Event e) {
        File schematic = (File) expressions[0].getSingle(e);
        Location location = (Location) expressions[2].getSingle(e);

        if (schematic == null || location == null) {
            return;
        }

        SchematicQueue.change(schematic, location);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "set location of " + expressions[0].toString(e, debug) + " to " + expressions[1].toString(e, debug);
    }
}
