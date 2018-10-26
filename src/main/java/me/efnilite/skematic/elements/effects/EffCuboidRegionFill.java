package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fill Region")
@Description("Fill a region (selection) with a block.")
public class EffCuboidRegionFill extends Effect {

    static {
        Skript.registerEffect(EffDelAsyncWorld.class, "fill [all] [the] blocks in %cuboidregions% to %string%");
    }

    private Expression<CuboidRegion> region;
    private Expression<String> pattern;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        region = (Expression<CuboidRegion>) exprs[0];
        pattern = (Expression<String>) exprs[1];

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        CuboidRegion r = region.getSingle(e);
        String p = pattern.getSingle(e);

        if (p == null || r == null) {
            return;
        }

        World w = r.getWorld();

        if (w == null) {
            return;
        }

        EditSession session = FaweAPI.getEditSessionBuilder(r.getWorld()).autoQueue(true).build();
        session.setBlocks(r,  new BlockPattern(new BaseBlock(BlockTypes.valueOf(p))));
        session.flushQueue();
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "fill " + region.toString(event, debug);
    }

}
