package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("Fill Region")
@Description("Fill a region (selection) with a block.")
@Since("2.1")
public class EffCuboidFill extends Effect {

    private Expression<CuboidRegion> cuboid;
    private Expression<ItemType> blocks;

    static {
        Skript.registerEffect(EffCuboidFill.class, "set [(all [[of] the]|the)] [(fawe|skematic)] blocks in %cuboidregions% to %itemtypes%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        blocks = (Expression<ItemType>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        ItemType[] blocks = this.blocks.getArray(e);

        if (cuboid == null || blocks == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.setBlocks(cuboid, FaweUtils.parsePattern(blocks));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fill " + cuboid.toString(e, debug) + " with " + blocks.toString(e, debug);
    }
}
