package com.efnilite.skematic.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Redo")
@Description("Redo the last undo of a player.")
@Examples("redo the last change of player")
@Patterns("redo [the] last [(fawe|fastasyncworldedit)] ((action|change) of %player%|%player%'s (action|change))")
public class EffRedo extends SkematicEffect {

    @Override
    protected void execute(Event e) {
        Player player = (Player) expressions[0].getSingle(e);

        if (player == null) {
            return;
        }

        EditSession session = FaweTools.getPlayer(player).getNewEditSession();
        session.redo(FaweTools.getPlayer(player).getNewEditSession());
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "redo last change of " + expressions[0].toString(e, debug);
    }
}
