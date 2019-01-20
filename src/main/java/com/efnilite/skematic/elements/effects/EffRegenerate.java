package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
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

@Name("Regenerate")
@Description("Regenerate a cuboidregion.")
@Since("2.0")
public class EffRegenerate extends Effect {

    private Expression<CuboidRegion> cuboid;

    static {
        Skript.registerEffect(EffRegenerate.class, "regenerate [cuboid[(-| )region]] %cuboidregions%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);

        if (cuboid == null) {
            return;
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getServer().getWorld(cuboid.getWorld().getName()));
        session.regenerate(cuboid);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "regenerate " + cuboid.toString(e, debug);
    }
}
