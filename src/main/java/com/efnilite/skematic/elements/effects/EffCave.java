package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Cavify")
@Description("Generate caves in a cuboidregion.")
public class EffCave extends SkematicEffect {

    static {
        Skript.registerEffect(EffCave.class, "cav(e|ify) %cuboidregions%");
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);

        if (cuboid == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        try {
            session.addCaves(cuboid);
        } catch (WorldEditException ex) {
            ex.printStackTrace();
        }
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "cavify " + expressions[0].toString(e, debug);
    }
}
