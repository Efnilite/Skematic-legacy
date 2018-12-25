package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.bukkit.wrapper.AsyncWorld;
import com.efnilite.skematic.lang.SkematicEffect;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("AsyncWorld - Save")
@Description("Save an AsyncWorld.")
@Examples("save the async world \"lobby-6\"")
public class EffAsyncWorldSave extends SkematicEffect {

    static {
        Skript.registerEffect(EffAsyncWorldSave.class, "save [the] [(fawe|skematic)] async[hronous] %world%");
    }

    @Override
    protected void execute(Event e) {
        World world = (World) expressions[0].getSingle(e);

        if (world == null) {
            return;
        }

        AsyncWorld.wrap(world).save();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "save async world " + expressions[0].toString(e, debug);
    }
}
