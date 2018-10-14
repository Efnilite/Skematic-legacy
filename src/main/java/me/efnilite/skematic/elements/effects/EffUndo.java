package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.EditSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffUndo extends Effect {

    static {
        Skript.registerEffect(EffUndo.class, "undo [the] last [(fawe|fastasyncworldedit)] ((action|change) of %player%");
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
        FawePlayer p = FaweAPI.wrapPlayer(player.getSingle(e));
        EditSession s = p.getNewEditSession();
        s.undo(s);
    }
}
