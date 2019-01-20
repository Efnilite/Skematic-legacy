package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
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

import java.util.List;

@Name("Block count")
@Description("Get the block count in a cuboidregion")
@Examples({"command /glass:",
            "\ttrigger:",
            "\t\tsend \"%size of glass in player's selection%\""})
@Since("2.0")
public class ExprCuboidRegionBlocksCount extends SimpleExpression<Number> {

    private Expression<ItemType> block;
    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerExpression(ExprCuboidRegionBlocksCount.class, Number.class, ExpressionType.PROPERTY,
                "[(skematic|fawe)] (size|amount) of %itemtypes% in [region] %cuboidregions%",
                "%cuboidregions%'[s] [(skematic|fawe)] (size|amount) of %itemtypes%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        block = (Expression<ItemType>) expressions[0];
        cuboid = (Expression<CuboidRegion>) expressions[1];

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        ItemType item = this.block.getSingle(e);
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null || item == null) {
            return null;
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        List<Countable<BlockStateHolder>> blocks = session.getBlockDistributionWithData(cuboid);

        for (Countable<BlockStateHolder> block : blocks) {
            if (Material.getMaterial(block.getID().getBlockType().getName()) == item.getRandom().getType()) {
                return new Number[] { block.getAmount() };
            }
        }
        return new Number[] { 0 };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "blocks of " + block.toString(e, debug) + " in " + cuboid.toString(e, debug);
    }
}
