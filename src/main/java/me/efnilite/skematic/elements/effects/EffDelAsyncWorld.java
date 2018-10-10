package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class EffDelAsyncWorld extends Effect {

    static {
        Skript.registerEffect(EffDelAsyncWorld.class, "del[ete] [the] [async[hronous]] [world] %object%");
    }

    private Expression<AsyncWorld> world;
    private Expression<Variable<AsyncWorld>> var;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        var = (Expression<Variable<AsyncWorld>>) exprs[0];
        world = (Expression<AsyncWorld>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "delete the async world " + world.toString(event, debug) + " stored in the variable " + var.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        world.getSingle(e).getWorldFolder().delete();
        var.change(e, CollectionUtils.array(world.getSingle(e)), Changer.ChangeMode.DELETE);
    }

}
