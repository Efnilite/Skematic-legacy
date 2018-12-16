package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("AsyncWorld - Delete")
@Description("Delete an AsyncWorld.")
@Examples("delete async world \"hi\"")
@Patterns("del[ete] [the] async[hronous] %world%")
public class EffAsyncWorldDelete extends SkematicEffect {

    static {
        Skript.registerEffect(EffAsyncWorldDelete.class, "del[ete] [the] async[hronous] %world%");
    }

    @Override
    protected void execute(Event e) {
        World world = (World) expressions[0].getSingle(e);

        if (world == null) {
            return;
        }

        AsyncWorld.wrap(world).getSaveFolder().delete();
    }

}
