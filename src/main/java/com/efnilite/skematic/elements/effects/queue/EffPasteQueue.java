package com.efnilite.skematic.elements.effects.queue;

import ch.njol.skript.Skript;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.event.Event;

public class EffPasteQueue extends SkematicEffect {

    static {
        Skript.registerEffect(EffPasteQueue.class, "(1¦paste [in-]queue[d] s(ch|k)ematic[s]|2¦paste (first[ly]|next) [in-]queue[d] s(k|ch|ematic)");
}

    @Override
    protected void execute(Event e) {
        if (mark == 1) {
            SchematicQueue.complete();
        } else if (mark == 2) {
            SchematicQueue.runNext();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "finish the queue";
    }
}
