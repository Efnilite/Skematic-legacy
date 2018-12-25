package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.efnilite.skematic.lang.SkematicEffect;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;

@Name("AsyncWorld - Create")
@Description("Create an AsyncWorld.")
@Examples("create the async world called \"myworld\"")
public class EffAsyncWorldCreate extends SkematicEffect {

    static {
        Skript.registerEffect(EffAsyncWorldCreate.class, "(create|load) [the] [(fawe|skematic)]async[hronous] [world] [(called|named)] %strings%",
                "(load|create) [(fawe|skematic)] world[s] [(called|named)] %strings% async[hronously]");
    }

    @Override
    protected void execute(Event e) {
        String w = (String) expressions[0].getSingle(e);

        if (w == null) {
            return;
        }

        AsyncWorld.create(new WorldCreator(w));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "load async world " + expressions[0].toString(e, debug);
    }
}
