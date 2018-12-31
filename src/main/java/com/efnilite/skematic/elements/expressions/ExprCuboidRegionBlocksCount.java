package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.util.Countable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;

import java.util.List;

@Name("Block count")
@Description("Get the block count in a cuboidregion")
@Examples({"command /glass:",
            "\ttrigger:",
            "\t\tsend \"%size of glass in player's selection%\""})
@Return(Number.class)
@Single
public class ExprCuboidRegionBlocksCount extends SkematicExpression<Number> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocksCount.class, Number.class, ExpressionType.PROPERTY, "[(skematic|fawe)] (size|amount) of %itemtypes% in [region] %cuboidregions%",
                "%cuboidregions%'[s] [(skematic|fawe)] (size|amount) of %itemtypes%");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected Number[] get(Event e) {
        ItemType item = (ItemType) expressions[0].getSingle(e);
        CuboidRegion cuboid = (CuboidRegion) expressions[1].getSingle(e);

        if (cuboid == null || item == null) {
            return null;
        }

        int count = 0;
        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        List<Countable<BaseBlock>> blocks = session.getBlockDistributionWithData(cuboid);

        for (Countable<BaseBlock> block : blocks) {
            if (Material.getMaterial(block.getID().getId()) == item.getRandom().getType()) {
                return new Number[] { block.getAmount() };
            }
        }
        return new Number[] { 0 };
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get blocks of " + expressions[0].toString(e, debug) + " in " + expressions[1].toString(e, debug);
    }
}
