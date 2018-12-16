package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.util.Countable;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

@Patterns({"[all] [of] [the] [skematic] blocks in %cuboidregions%", "[all] [of] %cuboidregions%'s [skematic] blocks"})
@Return(ItemType.class)
public class ExprCuboidRegionBlocks extends SkematicExpression<ItemType> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocks.class, ItemType.class, ExpressionType.PROPERTY,
                "[all] [of] [the] [skematic] blocks in %cuboidregions%",
                "[all] [of] %cuboidregions%'s [skematic] blocks");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ItemType[] get(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);

        if (cuboid == null) {
            return null;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        List<Countable<BaseBlock>> blocks = session.getBlockDistributionWithData(cuboid);
        List<ItemType> materials = new ArrayList<>();

        if (blocks == null) {
            return null;
        }

        for (Countable<BaseBlock> block : blocks) {
            materials.add(new ItemType(block.getID().getId()));
        }

        return materials.toArray(new ItemType[0]);
    }
}
