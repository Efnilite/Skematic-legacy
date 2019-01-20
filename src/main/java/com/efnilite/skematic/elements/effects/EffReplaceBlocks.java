package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.HashSet;
import java.util.Set;

@Name("Replace blocks")
@Description("Replace all blocks with other blocks in a cuboidregion")
@Examples("replace all fawe stone and gravel with blocks air in (player's selection)")
@Since("2.0")
public class EffReplaceBlocks extends Effect {

    private Expression<ItemType> target;
    private Expression<ItemType> replacement;
    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerEffect(EffReplaceBlocks.class, "replace [all] %itemtypes% [block[s]] with %itemtypes% [block[s]] in %cuboidregions%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        target = (Expression<ItemType>) expressions[0];
        replacement = (Expression<ItemType>) expressions[1];
        cuboid = (Expression<CuboidRegion>) expressions[2];

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        ItemType[] target = this.target.getArray(e);
        ItemType[] replacement = this.replacement.getArray(e);
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null || target == null || replacement == null) {
            return;
        }

        Set<BlockStateHolder> set = new HashSet<>();
        for (ItemType type : target) {
            set.add(new BaseBlock(type.getRandom().getType().getId(), type.getRandom().getAmount()));
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.replaceBlocks(cuboid, set, FaweUtils.parsePattern(replacement));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "replace all " + target.toString(e, debug) + " with " + replacement.toString(e, debug) + " in " + cuboid.toString(e, debug);
    }
}
