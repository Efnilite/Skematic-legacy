package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Redo")
@Description("Redo the last undo of a player.")
@Examples("redo the last change of player")
@Since("1.0")
public class EffRedo extends Effect {

    private Expression<Player> player;

    static {
        Skript.registerEffect(EffRedo.class, "redo [the] last [(fawe|fastasyncworldedit)] ((action|change) of %player%|%player%'s (action|change))");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Player player = this.player.getSingle(e);

        if (player == null) {
            return;
        }

        EditSession session = FaweUtils.getPlayer(player).getNewEditSession();
        session.redo(FaweUtils.getPlayer(player).getNewEditSession());
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "redo last change of " + player.toString(e, debug);
    }
}
