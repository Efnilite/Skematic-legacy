package me.efnilite.skematic.elements.effects;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

public class EffDelAsyncWorld extends Effect {

    private Expression<World> world;
    private Expression<Variable> var;


    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        var = (Expression<Variable>) exprs[0];
        world = (Expression<World>) exprs[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "Delete an async world.";
    }

    @Override
    protected void execute(Event e) {
        world.getSingle(e).getWorldFolder().delete();
        var.change(e, null, Changer.ChangeMode.DELETE);
    }

}
