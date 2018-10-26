package me.efnilite.skematic.elements.expressions;

/*
        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
 */

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.util.TaskManager;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;

public class ExprCuboidRegionBlocks extends SimpleExpression<Block> {

    static {
        Skript.registerExpression(ExprCuboidRegionBlocks.class, Block.class, ExpressionType.PROPERTY,
                "[all] [of] [the] [skematic] blocks in %cuboidregions%",
                "[all] [of] %cuboidregions%'s [skematic] blocks");
    }

    private Expression<CuboidRegion> region;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        return true;
    }

    @Override
    protected Block[] get(Event e) {
        CuboidRegion r = region.getSingle(e);

        if (r == null) {
            return null;
        }

        Vector pos1 = r.getPos1();
        Vector pos2 = r.getPos2();

        ArrayList<Block> blocks = new ArrayList<>();
        for (int x = pos1.getBlockX(); x <= pos2.getBlockX(); x++) {
            for (int y = pos1.getBlockY(); y <= pos2.getBlockY(); y++) {
                for (int z = pos1.getBlockZ(); z <= pos2.getBlockZ(); z++) {
                    Block block = Bukkit.getServer().getWorld(r.getWorld().getName()).getBlockAt(x, y, z);
                    blocks.add(block);
                }
            }
        }
        return blocks.toArray(new Block[0]);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "dimensions of " + region.toString(e, debug);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Block> getReturnType() {
        return Block.class;
    }
}
