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
import com.boydti.fawe.wrappers.WorldWrapper;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.World;
import me.efnilite.skematic.utils.FaweTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Greenify")
@Description("Greenify an area - Turns it into grass.")
@Examples("greenify 2, 3, 4 in \"world\" within a radius of 20")
@Since("1.0.0")
public class EffGreen extends Effect {

    static {
        Skript.registerEffect(EffGreen.class, "(green|grass)[ify] %location% (in|within) [a] radius [of] %number%");
    }

    private Expression<Location> position;
    private Expression<Number> radius;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        position = (Expression<Location>) exprs[0];
        radius = (Expression<Number>) exprs[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Location l = position.getSingle(e);
        Number r = radius.getSingle(e);

        if (r == null || l == null) {
            return;
        }

        EditSession s = FaweTools.getEditSession(l.getWorld());
        s.green(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()), (double) r);
        s.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "greenify " + position.toString(e, debug) + " with radius " + radius.toString(e, debug);
    }


}
