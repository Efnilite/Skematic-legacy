package me.efnilite.skematic.elements.hooks.worldguard.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.efnilite.skematic.elements.hooks.worldguard.WorldGuard;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.World;
import org.bukkit.event.Event;

public class EffDelRegion extends Effect {

    private Expression<String> name;
    private Expression<World> world;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];

        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "delete region " + name.toString(e, debug) + " in world " + world.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        try {
            WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)).removeRegion(name.getSingle(e));
        } catch (NullPointerException exception) {
            Utilities.error("Could not delete region " + name.toString() + ". Is the world " + world.toString() + " blacklisted?", exception, false);
        }
    }
}
