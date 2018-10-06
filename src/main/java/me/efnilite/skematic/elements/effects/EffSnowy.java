package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffSnowy extends Effect {

    static {
        Skript.registerEffect(EffSnowy.class, "(sim[ulate]|place) snow at %location% with [the] radius %number% using (%player%'s edit(-| )session|edit(-| )session of %player%)");
    }

    private Expression<Location> position;
    private Expression<Number> radius;
    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position = (Expression<Location>) exprs[0];
        radius = (Expression<Number>) exprs[1];
        player = (Expression<Player>) exprs[2];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "simulate snow at " + position.toString(e, debug) + " with radius " + radius.toString(e, debug) + " using the editsession of player " + player.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        FaweAPI.wrapPlayer(player.getSingle(e)).getNewEditSession().simulateSnow(new Vector(position.getSingle(e).getBlockX(), position.getSingle(e).getBlockY(), position.getSingle(e).getBlockZ()), (double) radius.getSingle(e));
    }
}
