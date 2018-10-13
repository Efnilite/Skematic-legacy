package me.efnilite.skematic.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class CondSelectionContains extends Condition {

    static {
        Skript.registerCondition(CondSelectionContains.class, "%selections% contains %location%");
    }

    private Expression<Player> player;
    private Expression<Location> location;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];
        location = (Expression<Location>) exprs[1];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "test if " + location.toString(e, debug) + " is in the selection of " + player.toString(e, debug);
    }

    @Override
    public boolean check(Event e) {

        Location pos = location.getSingle(e);

        if (!FaweAPI.wrapPlayer(player.toString()).getSelection().contains(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ())) return !isNegated();
        return isNegated();

    }
}
