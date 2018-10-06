package me.efnilite.skematic.hooks.worldguard.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.efnilite.skematic.Skematic;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import org.bukkit.World;
import org.bukkit.event.Event;

import java.util.logging.Level;

public class EffDelRegion extends Effect {

    static {
        Skript.registerEffect(EffDelRegion.class, "del[ete] [the] [worldguard] region [(named|call)] %string% in [world] %world%");
    }

    private Expression<String> name;
    private Expression<World> world;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        world = (Expression<World>) exprs[1];

        return true;
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
            Skematic.log("Could not delete region " + name.toString() + ". Is the world " + world.toString() + " blacklisted?", Level.SEVERE);
        }
    }
}
