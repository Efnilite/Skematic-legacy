package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import org.bukkit.event.Event;

public class EffNaturalize extends Effect {

    static {
        Skript.registerEffect(EffNaturalize.class, "naturalize %region% in %world%");
    }

    private Expression<World> world;
    private Expression<Region> region;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        region = (Expression<Region>) exprs[0];
        world = (Expression<World>) exprs[1];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "naturalize region " + region.toString(e, debug) + " in world " + world.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {

        EditSession session = FaweAPI.getEditSessionBuilder(world.getSingle(e)).autoQueue(true).build();
        session.naturalizeCuboidBlocks(region.getSingle(e));
    }

}
