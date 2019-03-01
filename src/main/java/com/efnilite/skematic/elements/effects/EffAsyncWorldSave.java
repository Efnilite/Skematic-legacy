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
import org.bukkit.event.Event;

@Name("AsyncWorld - Save")
@Description("Save an AsyncWorld.")
@Examples("save the async world \"lobby-6\"")
@Since("1.0")
public class EffAsyncWorldSave extends Effect {

    private Expression<World> world;

    static {
        Skript.registerEffect(EffAsyncWorldSave.class, "save [the] [(fawe|skematic)] async[hronous] %world%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        world = (Expression<World>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        World world = this.world.getSingle(e);

        if (world == null) {
            return;
        }

        AsyncWorld.wrap(world).save();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "save async world " + world.toString(e, debug);
    }
}
