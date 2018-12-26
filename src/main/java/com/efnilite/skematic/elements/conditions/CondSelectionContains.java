package com.efnilite.skematic.elements.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicCondition;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Selection contains")
@Description("Check whether the selection of a player contains a location.")
public class CondSelectionContains extends SkematicCondition {

    static {
        Skript.registerCondition(CondSelectionContains.class, "%cuboidregions% contains %location%");
    }

    @Override
    public boolean check(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        Location location = (Location) expressions[1].getSingle(e);

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
        return "if " + expressions[1].toString(e, debug) + " is in " + expressions[0].toString(e, debug);
    }
}
