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

public class EffGreen extends Effect {

    static {
        Skript.registerEffect(EffGreen.class, "green[ify] %location%[(,| and)] [with] radius %number% using [the] (%player%'s edit(-| )session|edit(-| )session of %player%)");
    }

    private Expression<Player> player;
    private Expression<Location> position;
    private Expression<Number> radius;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position = (Expression<Location>) exprs[0];
        radius = (Expression<Number>) exprs[1];
        player = (Expression<Player>) exprs[2];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "greenify " + position.toString(e, debug) + " with radius " + radius.toString(e, debug) + " using the editsession of " + player.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        FaweAPI.wrapPlayer(player.toString()).getNewEditSession().green(new Vector(position.getSingle(e).getBlockX(), position.getSingle(e).getBlockY(), position.getSingle(e).getBlockZ()), (double) radius.getSingle(e));
    }

}
