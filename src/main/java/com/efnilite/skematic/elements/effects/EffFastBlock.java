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
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Fastplace")
@Description("Place a block fast at a location.")
@Examples("fastplace stone at {_location}")
public class EffFastBlock extends SkematicEffect {

    static {
        Skript.registerEffect(EffFastBlock.class, "fast[( |-|)]place %itemtype% at %location%");
    }

    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    protected void execute(Event e) {
        ItemType type = (ItemType) expressions[0].getSingle(e);
        Location location = (Location) expressions[1].getSingle(e);

        if (type == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(location.getWorld());
        session.setBlockFast(FaweUtils.toVector(location), new BaseBlock(type.getRandom().getType().getId()));
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "place fast at " + expressions[0].toString(e, debug);
    }
}
