package com.efnilite.skematic.elements.effects;

import ch.njol.skript.aliases.ItemType;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.HashSet;
import java.util.Set;

@Patterns("replace [all] [(fawe|skematic)] (block[s]|item[type[s]]) %itemtypes% with (block[s]|item[type[s]]) %itemtypes% in %cuboidregions%")
public class EffReplaceBlocks extends SkematicEffect {

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        ItemType[] target = (ItemType[]) expressions[0].getSingle(e);
        ItemType[] replacement = (ItemType[]) expressions[1].getSingle(e);
        CuboidRegion cuboid = (CuboidRegion) expressions[2].getSingle(e);

        if (cuboid == null || target == null || replacement == null) {
            return;
        }

        Set<BaseBlock> set = new HashSet<>();
        for (ItemType type : target) {
            set.add(new BaseBlock(type.getRandom().getType().getId()));
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.replaceBlocks(cuboid, set, FaweTools.parsePattern(replacement));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "place fast at " + expressions[0].toString(e, debug);
    }
}
