package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Redo")
@Description("Redo the last undo of a player.")
@Examples("redo the last change of player")
@Since("1.0.0")
public class EffRedo extends Effect {

    static {
        Skript.registerEffect(EffRedo.class, "redo [the] last [(fawe|fastasyncworldedit)] ((action|change) of %player%|%player%'s (action|change))");
    }

    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "redo last change of " + player.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {

        Player p = player.getSingle(e);

        if (p == null) {
            return;
        }

        FaweAPI.wrapPlayer(p).getNewEditSession().redo(FaweAPI.wrapPlayer(p).getNewEditSession());
    }

}
