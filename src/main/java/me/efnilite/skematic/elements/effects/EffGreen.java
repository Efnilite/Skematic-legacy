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

@Name("Greenify")
@Description("Greenify an area - Turns it into grass.")
@Examples("greenify 2, 3, 4 in \"world\" within a radius of 20")
@Since("1.0.0")
public class EffGreen extends Effect {

    static {
        Skript.registerEffect(EffGreen.class, "(green|grass)[ify] %location% in %world% (in|within) [a] radius [of] %number%");
    }

    private Expression<World> world;
    private Expression<Location> position;
    private Expression<Number> radius;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        position = (Expression<Location>) exprs[0];
        radius = (Expression<Number>) exprs[2];
        world = (Expression<World>) exprs[1];

        return true;
    }

    @Override
    protected void execute(Event e) {

        Location l = position.getSingle(e);
        World w = world.getSingle(e);
        Number r = radius.getSingle(e);

        if (r == null || w == null || l == null) {
            return;
        }

        EditSession s = FaweAPI.getEditSessionBuilder(w).autoQueue(true).build();
        s.green(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()), (double) r);
        s.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "greenify " + position.toString(e, debug) + " with radius " + radius.toString(e, debug) + " in world " + world.toString(e, debug);
    }


}
