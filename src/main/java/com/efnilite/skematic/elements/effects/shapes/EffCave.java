package com.efnilite.skematic.elements.effects.shapes;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Cavify")
@Description("Generate caves in a cuboidregion.")
@Examples("cavify {the cuboid region}")
@Patterns("cav(e|ify) %cuboidregions%")
public class EffCave extends SkematicEffect {

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);

        if (cuboid == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        try {
            session.addCaves(cuboid);
        } catch (WorldEditException ex) {
            ex.printStackTrace();
        }
        session.flushQueue();
    }
}
