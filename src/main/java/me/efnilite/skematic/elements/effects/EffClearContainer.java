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
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Clear contents")
@Description("Clear the contents of a container at a location.")
@Examples("clear the contents of the container at 3, 73, 12 in \"world\"")
@Since("1.0.0")
public class EffClearContainer extends Effect {

    static {
        Skript.registerEffect(EffClearContainer.class, "(clear|delete) [the] [(fawe|skematic)] content[s] of [the] [container] at %location%");
    }

    private Expression<World> world;
    private Expression<Location> position;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position = (Expression<Location>) exprs[0];
        world = (Expression<World>) exprs[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location l = position.getSingle(e);
        World w = world.getSingle(e);

        if (w == null || l == null) {
            return;
        }

        EditSession s = FaweAPI.getEditSessionBuilder(w).autoQueue(true).build();
        s.clearContainerBlockContents(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()));
        s.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "clear the contents of the container at " + position.toString(e, debug) + " in world " + world.toString(e, debug);
    }

}
