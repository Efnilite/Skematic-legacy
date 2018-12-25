package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.util.Countable;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Name("All blocks")
@Description("Get all blocks in a cuboidregion")
@Return(ItemType.class)
public class ExprCuboidRegionBlocks extends SkematicExpression<ItemType> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocks.class, ItemType.class, ExpressionType.PROPERTY, "[all] [of] [the] [skematic] blocks in %cuboidregions%",
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
        List<Countable<BlockStateHolder>> blocks = session.getBlockDistributionWithData(cuboid);
        List<ItemType> materials = new ArrayList<>();

        if (blocks == null) {
            return null;
        }

        for (Countable<BlockStateHolder> block : blocks) {
            materials.add(new ItemType(new ItemStack(Material.valueOf(block.getID().getBlockType().getName()))));
        }

        return materials.toArray(new ItemType[0]);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get all blocks in " + expressions[0].toString(e, debug);
    }
}
