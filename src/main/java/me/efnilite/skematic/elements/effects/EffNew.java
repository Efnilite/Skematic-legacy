package me.efnilite.skematic.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import org.bukkit.event.Event;

public class EffNew extends Effect {

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    protected void execute(Event e) {
        FaweAPI.getWorld("hi");
    }
}
