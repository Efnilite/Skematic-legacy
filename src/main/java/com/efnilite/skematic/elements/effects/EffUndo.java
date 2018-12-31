package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Undo")
@Description("Undo the last edit of a player.")
@Examples("undo the last change of player")
public class EffUndo extends SkematicEffect {

    static {
        Skript.registerEffect(EffUndo.class, "undo [the] last [(fawe|fastasyncworldedit)] (action|change) of %player%");
    }

    @Override
    protected void execute(Event e) {
        Player player = (Player) expressions[0].getSingle(e);

        if (player == null) {
            return;
        }

        EditSession session = FaweTools.getPlayer(player).getNewEditSession();
        session.undo(session);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "undo last change of " + expressions[0].toString(e, debug);
    }
}
