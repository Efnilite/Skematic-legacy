package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Flush Queue")
@Description("Flush the queue of a player - clear it.")
@Examples("flush player's queue")
@Patterns("(flush|dequeue) [the] (queue of %player%|%player%'s queue)")
public class EffFlush extends SkematicEffect {

    static {
        Skript.registerEffect(EffFlush.class, "(flush|dequeue) [the] (queue of %player%|%player%'s queue)");
    }

    @Override
    protected void execute(Event e) {
        Player player = (Player) expressions[0].getSingle(e);

        if (player == null) {
            return;
        }

        FaweTools.getPlayer(player).getNewEditSession().flushQueue();
    }
}
