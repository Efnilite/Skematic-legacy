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
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.mask.Masks;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.efnilite.skematic.utils.FaweTools;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Drain liquids")
@Description("Drain all liquids at a certain location with a radius.")
@Examples("drain all liquids at 124, 32, 12 in \"world\" in a radius of 10")
@Since("1.0.1")
public class EffDrain extends Effect {

    static {
        Skript.registerEffect(EffDrain.class, "drain [all] [(skematic|fawe)] [liquid[s]] [at] %location% (in|within) [a] radius [of] %number%");
    }

    private Expression<Location> position;
    private Expression<Number> radius;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

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
        s.drainArea(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()), (double) r);
        s.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "drain area " + position.toString(e, debug) + " with radius " + radius.toString(e, debug);
    }


}
