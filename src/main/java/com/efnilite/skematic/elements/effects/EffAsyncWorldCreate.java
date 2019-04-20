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
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;

@Name("AsyncWorld - Create")
@Description("Create an AsyncWorld.")
@Examples("create the async world called \"myworld\"")
@Since("1.0")
public class EffAsyncWorldCreate extends Effect {

    private Expression<String> name;
    private Expression<World> world;

    static {
        Skript.registerEffect(EffAsyncWorldCreate.class, "(create|load) [the] [(fawe|skematic)]async[hronous] [world] [(called|named)] %strings% [(1¦using [world[s]] %-world%)]",
                "(load|create) [(fawe|skematic)] world[s] [(called|named)] %strings% async[hronously] [(1¦using [world[s]] %-world%)]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) expressions[0];
        if (parseResult.mark == 1) {
            world = (Expression<World>) expressions[1];
        }

        return true;
    }

    @Override
    protected void execute(Event e) {
        String name = this.name.getSingle(e);
        World world = this.world.getSingle(e);

        if (name == null) {
            return;
        }

        if (world == null) {
            AsyncWorld.create(new WorldCreator(name));
        } else {
            AsyncWorld.create(new WorldCreator(name)
                    .copy(world));
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "load world " + world.toString(e, debug);
    }
}
