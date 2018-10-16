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
import org.bukkit.World;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("AsyncWorld - Delete")
@Description("Delete an AsyncWorld.")
@Examples("delete async world \"hi\"")
@Since("1.0.0")
public class EffDelAsyncWorld extends Effect {

    static {
        Skript.registerEffect(EffDelAsyncWorld.class, "del[ete] [the] async[hronous] [world] %world%");
    }

    private Expression<AsyncWorld> world;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        world = (Expression<AsyncWorld>) exprs[0];

        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "delete the async world " + world.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {

        World w = world.getSingle(e);

        if (w == null) {
            return;
        }

        w.getWorldFolder().delete();
    }

}
