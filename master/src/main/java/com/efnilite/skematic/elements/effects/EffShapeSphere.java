package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Shape - Sphere")
@Description("Create a (hollow) sphere at a location")
public class EffShapeSphere extends SkematicEffect {

    static {
        Skript.registerEffect(EffShapeSphere.class, "(make|create) [a] [new] [(1¦hollow)] sphere %direction% %location% (with|and) pattern %string% (with|and) radius %number%");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        Location location = (Location) Direction.combine((Expression<Direction>) expressions[0], (Expression<Location>) expressions[1]);
        ItemType[] blocks = (ItemType[]) expressions[2].getAll(e);
        Number size = (Number) expressions[3].getSingle(e);
        boolean filled = true;

        if (blocks == null || size == null) {
            return;
        }

        if (mark == 1) {
            filled = false;
        }

        EditSession session = FaweTools.getEditSession(location.getWorld());
        session.makeSphere(new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ()), FaweTools.parsePattern(blocks), Math.round((long) size), filled);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "create sphere at " + expressions[1].toString(e, debug) + " with blocks " + expressions[1].toString(e, debug) + " with radius " + expressions[2].toString(e, debug);
    }
}
