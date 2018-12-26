package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Regenerate")
@Description("Regenerate a cuboidregion.")
public class EffRegenerate extends SkematicEffect {

    static {
        Skript.registerEffect(EffRegenerate.class, "regenerate [cuboid[(-| )region]] %cuboidregions%");
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);

        if (cuboid == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.regenerate(cuboid);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "regenerate " + expressions[0].toString(e, debug);
    }
}
