package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.Aliases;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.object.pattern.LinearBlockPattern;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.function.pattern.ClipboardPattern;
import com.sk89q.worldedit.function.pattern.Patterns;
import com.sk89q.worldedit.patterns.Pattern;
import com.sk89q.worldedit.patterns.SingleBlockPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.efnilite.skematic.utils.FaweTools;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Fill Region")
@Description("Fill a region (selection) with a block.")
public class EffCuboidRegionFill extends Effect {

    static {
        Skript.registerEffect(EffCuboidRegionFill.class, "fill [all] [the] blocks in %cuboidregions% to %itemtype%");
    }

    private Expression<CuboidRegion> region;
    private Expression<ItemType> pattern;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        region = (Expression<CuboidRegion>) exprs[0];
        pattern = (Expression<ItemType>) exprs[1];

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        CuboidRegion cuboid = region.getSingle(e);
        ItemType item = pattern.getSingle(e);

        if (cuboid == null || item == null) {
            return;
        }

        World world = cuboid.getWorld();

        if (world == null) {
            return;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(world.getName()));
        Pattern blockPattern = new SingleBlockPattern(new BaseBlock(BlockTypes.valueOf(item.toString()).getInternalId(), 0));
        session.setBlocks(cuboid, Patterns.wrap(blockPattern));
        session.flushQueue();
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "fill " + region.toString(event, debug);
    }

}
