package me.efnilite.skematic.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.world.weather.WeatherTypes;
import org.bukkit.World;
import org.bukkit.event.Event;

public class EffWeather extends Effect {

    private int mark;
    private Expression<World> world;
    private Expression<Long> duration;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        mark = parseResult.mark;
        world = (Expression<World>) exprs[0];
        duration = (Expression<Long>) exprs[1];

        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "set the weather in world " + world.toString(e, debug) + " to " + mark + " for " + duration.toString(e, debug) + " seconds";
    }

    @Override
    protected void execute(Event e) {
        switch (mark) {
            case 1:
                FaweAPI.getWorld(world.toString()).setWeather(WeatherTypes.RAIN, duration.getSingle(e));
                break;
            case 2:
                FaweAPI.getWorld(world.toString()).setWeather(WeatherTypes.THUNDER_STORM, duration.getSingle(e));
                break;
            case 3:
                FaweAPI.getWorld(world.toString()).setWeather(WeatherTypes.CLEAR, duration.getSingle(e));
                break;
        }
    }
}
