package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("AsyncWorld - Create")
@Description("Create an AsyncWorld.")
@Examples("create the async world called \"myworld\"")
@Since("1.0.0")
public class EffNewAsyncWorld extends Effect {

    static {
        Skript.registerEffect(EffNewAsyncWorld.class, "[skematic] (create|load) [the] async[hronous] [world] [(called|named)] %strings%",
                                                    "[skematic] (load|create) world[s] [(called|named)] %strings% async[hronously]");
    }

    private Expression<String> world;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        world = (Expression<String>) exprs[0];

        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "load the new async world " + world.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        String w = world.getSingle(e);

        if (w == null) {
            return;
        }

        AsyncWorld.create(new WorldCreator(w));
    }
}
