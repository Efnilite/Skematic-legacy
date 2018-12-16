package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.util.Countable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;

import java.util.List;

@Patterns({"[skematic] (size|amount) of %itemtypes% in [region] %cuboidregions%",
            "%cuboidregions%'s (size|amount) of %itemtypes%"})
@Return(Number.class)
@Single
public class ExprCuboidRegionBlocksCount extends SkematicExpression<Number> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocksCount.class, Number.class, ExpressionType.PROPERTY,
                "[skematic] (size|amount) of %itemtypes% in [region] %cuboidregions%",
                "%cuboidregions%'s (size|amount) of %itemtypes%");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected Number[] get(Event e) {
        ItemType m = (ItemType) expressions[0].getSingle(e);
        CuboidRegion r = (CuboidRegion) expressions[1].getSingle(e);

        if (r == null || m == null) {
            return null;
        }

        int count = 0;
        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(r.getWorld().getName()));
        List<Countable<BaseBlock>> blocks = session.getBlockDistributionWithData(r);

        for (Countable<BaseBlock> block : blocks) {
            if (Material.getMaterial(block.getID().getId()) == m.getRandom().getType()) {
                count = block.getAmount();
            }
        }

        return new Number[] { count };
    }
}
