package com.efnilite.skematic.elements.effects;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Fill Region")
@Description("Fill a region (selection) with a block.")
@Patterns({"set [(all [[of] the]|the)] [(fawe|skematic)] blocks in %cuboidregions% to %itemtypes%"})
public class EffCuboidRegionFill extends SkematicEffect {

    @Override
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        ItemType[] blocks = (ItemType[]) expressions[1].getAll(e);

        if (cuboid == null || blocks == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.setBlocks(cuboid, FaweTools.parsePattern(blocks));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fill" + expressions[0].toString(e, debug) + " with " + expressions[1].toString(e, debug);
    }
}
