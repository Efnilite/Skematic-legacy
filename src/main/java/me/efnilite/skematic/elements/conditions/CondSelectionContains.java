package me.efnilite.skematic.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Selection contains")
@Description("Check whether the selection of a player contains a location.")
@Examples("player's selection contains location 0, 0, 0 in \"World\"")
@Since("1.0.0")
public class CondSelectionContains extends Condition {

    static {
        Skript.registerCondition(CondSelectionContains.class, "%cuboidregions% contains %location%");
    }

    private Expression<Region> region;
    private Expression<Location> location;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        region = (Expression<Region>) exprs[0];
        location = (Expression<Location>) exprs[1];

        return true;
    }

    @Override
    public boolean check(Event e) {

        Location l = location.getSingle(e);
        Region r = region.getSingle(e);

        if (r == null || l == null) {
            return false;
        }

        if (!r.contains(l.getBlockX(), l.getBlockY(), l.getBlockZ())) {
            return !isNegated();
        } else {
            return isNegated();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "test if " + location.toString(e, debug) + " is in the region " + region.toString(e, debug);
    }

}
