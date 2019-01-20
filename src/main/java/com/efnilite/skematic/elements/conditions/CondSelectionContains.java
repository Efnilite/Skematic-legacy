package com.efnilite.skematic.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Selection contains")
@Description("Check whether the selection of a player contains a location.")
@Since("1.0")
public class CondSelectionContains extends Condition {

    private Expression<CuboidRegion> cuboid;
    private Expression<Location> location;

    static {
        Skript.registerCondition(CondSelectionContains.class, "%cuboidregions% contains %location%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        location = (Expression<Location>) expressions[1];

        return true;
    }

    @Override
    public boolean check(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        Location location = this.location.getSingle(e);

        if (cuboid == null || location == null) {
            return false;
        }

        if (!cuboid.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
            return !isNegated();
        } else {
            return isNegated();
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "if " + cuboid.toString(e, debug) + " contains " + location.toString(e, debug);
    }
}
