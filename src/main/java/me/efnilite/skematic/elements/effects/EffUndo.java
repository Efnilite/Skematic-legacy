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
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.EditSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Undo")
@Description("Undo the last edit of a player.")
@Examples("undo the last change of player")
@Since("1.0.0")
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
        EditSession s = FaweAPI.wrapPlayer(player.getSingle(e)).getNewEditSession();
        s.undo(s);
    }
}
