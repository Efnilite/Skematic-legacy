package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import me.efnilite.skematic.Skematic;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.util.logging.Level;

@Name("AsyncWorld - Create")
@Description("Create an AsyncWorld and save it as a variable. (required)")
@Examples("create the async world called \"myworld\" saved as {myworld}")
@Since("1.0.0")
public class EffNewAsyncWorld extends Effect {

    static {
        Skript.registerEffect(EffNewAsyncWorld.class, "(create|load) [the] async[hronous] [world] [called] %world% [sav(e[d]|ing)] (as|to) %object%");
    }

    private Expression<World> world;
    private Expression<Variable> save;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        world = (Expression<World>) exprs[0];
        if (exprs[1] instanceof Variable) {
            save = (Expression<Variable>) exprs[1];
        } else {
            Skematic.log(save + " is not a variable! Use variables to save AsyncWorlds.", Level.SEVERE);
        }
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "load the new async world " + world.toString(event, debug) + " stored in variable " + save.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        if (save != null) {
            AsyncWorld.create(new WorldCreator(world.getSingle(e).getName()));
            save.change(e, CollectionUtils.array(world.getSingle(e)), Changer.ChangeMode.SET);
        }
    }
}
