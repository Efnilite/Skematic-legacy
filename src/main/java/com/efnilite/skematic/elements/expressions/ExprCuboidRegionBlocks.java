package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
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
@Description("Get all blocks types in a cuboidregion.")
@Since("2.0")
public class ExprCuboidRegionBlocks extends SimpleExpression<ItemType> {

    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerExpression(ExprCuboidRegionBlocks.class, ItemType.class, ExpressionType.PROPERTY, "[(all [[of] the]|the)] [skematic] blocks in %cuboidregions%",
                "[(all [[of] the]|the)] %cuboidregions%'[s] [skematic] blocks");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];

        return true;
    }

    @Override
    protected ItemType[] get(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null) {
            return null;
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        List<Countable<BlockStateHolder>> blocks = session.getBlockDistributionWithData(cuboid);
        List<ItemType> materials = new ArrayList<>();

        if (blocks == null) {
            return null;
        }

        for (Countable<BlockStateHolder> block : blocks) {
            materials.add(new ItemType(new ItemStack(Material.valueOf(block.getID().getBlockType().getName().replace(" ", "_").toUpperCase()))));
        }

        return materials.toArray(new ItemType[0]);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "blocks in " + cuboid.toString(e, debug);
    }
}
