package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Clear contents")
@Description("Clear the contents of a container at a location.")
@Examples("clear the contents of the container at 3, 73, 12 in \"world\"")
@Patterns("(clear|delete) [the] [(fawe|skematic)] content[s] of [the] [container] at %location%")
public class EffClearContainer extends SkematicEffect {

    static {
        Skript.registerEffect(EffClearContainer.class, "(clear|delete) [the] [(fawe|skematic)] content[s] of [the] [container] at %location%");
    }

    @Override
    protected void execute(Event e) {
        Location location = (Location) expressions[0].getSingle(e);

        if (location == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.clearContainerBlockContents(new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        session.flushQueue();
    }

}
