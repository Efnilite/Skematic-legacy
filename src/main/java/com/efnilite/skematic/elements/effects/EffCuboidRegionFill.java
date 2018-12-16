package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Fill Region")
@Description("Fill a region (selection) with a block.")
@Patterns("fill [all] [the] blocks in %cuboidregions% to %itemtype%")
public class EffCuboidRegionFill extends SkematicEffect {

    static {
        Skript.registerEffect(EffCuboidRegionFill.class, "fill [all] [the] blocks in %cuboidregions% to %itemtype%");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        ItemType item = (ItemType) expressions[1].getSingle(e);

        if (cuboid == null || item == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        Pattern blockPattern = new BlockPattern(new BaseBlock(item.getRandom().getType().getId()));
        session.setBlocks(cuboid, com.sk89q.worldedit.function.pattern.Patterns.wrap(blockPattern));
        session.flushQueue();
    }
}
