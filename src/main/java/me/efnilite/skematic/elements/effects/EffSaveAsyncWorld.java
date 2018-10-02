package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class EffSaveAsyncWorld extends Effect {

    static {
        Skript.registerEffect(EffSaveAsyncWorld.class, "save [the] [async[hronous]] [world] %object%");
    }

    private Expression<World> world;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        world = (Expression<World>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "save the async world " + world.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        ((AsyncWorld) world).commit();
    }

}
