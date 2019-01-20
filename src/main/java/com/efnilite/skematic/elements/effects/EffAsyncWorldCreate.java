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

@Name("AsyncWorld - Create")
@Description("Create an AsyncWorld.")
@Examples("create the async world called \"myworld\"")
@Since("1.0")
public class EffAsyncWorldCreate extends Effect {

    private Expression<String> world;

    static {
        Skript.registerEffect(EffAsyncWorldCreate.class, "(create|load) [the] [(fawe|skematic)]async[hronous] [world] [(called|named)] %strings%",
                "(load|create) [(fawe|skematic)] world[s] [(called|named)] %strings% async[hronously]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        world = (Expression<String>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        String world = this.world.getSingle(e);

        if (world == null) {
            return;
        }

        AsyncWorld.create(new WorldCreator(world));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "load async world " + world.toString(e, debug);
    }
}
