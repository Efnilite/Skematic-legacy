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
import com.boydti.fawe.util.TaskManager;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class EffNewAsyncWorld extends Effect {

    static {
        Skript.registerEffect(EffNewAsyncWorld.class, "(create|load) [a] [new] [async[hronous]] [world] %object% [sav[(e[d]|ing)]] (as|to) %object%");
    }

    private Expression<String> world;
    private Expression<Variable> save;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        world = (Expression<String>) exprs[0];
        if (exprs[1] instanceof Variable) {
            save = (Expression<Variable>) exprs[1];
        } else {
            Utilities.error(save + " is not a variable! Use variables to save AsyncWorlds.", null, false);
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "load the new async world " + world.toString(event, debug) + " stored in variable " + save.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        if (world != null && save != null) {
            AsyncWorld.create(new WorldCreator(world.toString().replaceAll("\"", "")));
            save.change(e, CollectionUtils.array(world.getSingle(e)), Changer.ChangeMode.SET);
        }
    }
}
