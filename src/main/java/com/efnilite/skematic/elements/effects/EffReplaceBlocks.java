package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.efnilite.skematic.lang.SkematicEffect;
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
public class EffReplaceBlocks extends SkematicEffect {

    static {
        Skript.registerEffect(EffReplaceBlocks.class, "replace [all] %itemtypes% [block[s]] with %itemtypes% [block[s]] in %cuboidregions%");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        ItemType[] target = (ItemType[]) expressions[0].getSingle(e);
        ItemType[] replacement = (ItemType[]) expressions[1].getSingle(e);
        CuboidRegion cuboid = (CuboidRegion) expressions[2].getSingle(e);

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
        return "place fast at " + expressions[0].toString(e, debug);
    }
}
