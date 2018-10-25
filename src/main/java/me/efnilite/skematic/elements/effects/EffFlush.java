package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffFlush extends Effect {

    static {
        Skript.registerEffect(EffFlush.class, "(flush|dequeue) [the] (queue of %player%|%player%'s queue)");
    }

    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Player p = player.getSingle(e);

        if (p == null) {
            return;
        }

        FaweAPI.wrapPlayer(p).getNewEditSession().flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "flush " + player.toString(e, debug);
    }

}
