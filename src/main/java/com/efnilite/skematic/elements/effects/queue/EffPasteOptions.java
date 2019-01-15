package com.efnilite.skematic.elements.effects.queue;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.PasteOptions;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.event.Event;

import java.io.File;

@Name("Ignore pasteoptions")
public class EffPasteOptions extends SkematicEffect {

    static {
        Skript.registerEffect(EffPasteOptions.class, "make %skematics% ignore %pasteoptions%");
    }

    @Override
    protected void execute(Event e) {
        File schematic = (File) expressions[0].getSingle(e);
        PasteOptions[] options = (PasteOptions[]) expressions[2].getAll(e);

        if (schematic == null || options == null) {
            return;
        }

        SchematicQueue.change(schematic, options);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make " + expressions[0].toString(e, debug) + " ignore " + expressions[1].toString(e, debug);
    }
}
