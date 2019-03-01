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
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Clear contents")
@Description("Clear the contents of a container at a location.")
@Examples("clear the contents of the container at 3, 73, 12 in \"world\"")
@Since("1.5")
public class EffClearContainer extends Effect {

    private Expression<Location> location;

    static {
        Skript.registerEffect(EffClearContainer.class, "(clear|delete) [the] [(fawe|skematic)] content[s] of [the] [container] at %location%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        location = (Expression<Location>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location location = this.location.getSingle(e);

        if (location == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.clearContainerBlockContents(FaweUtils.toVector(location));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "clear contents of " + location.toString(e, debug);
    }
}
