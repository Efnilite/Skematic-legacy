package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffUndoPlayer extends Effect {

    static {
        Skript.registerEffect(EffUndoPlayer.class, "undo [the] last [(fawe|fastasyncworldedit)] ((action|change) of %player%|%player%'s (action|change))");
    }

    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "undo last change of " + player.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        FaweAPI.wrapPlayer(player.getSingle(e)).getNewEditSession().undo(FaweAPI.wrapPlayer(player.getSingle(e)).getNewEditSession());
    }
}
