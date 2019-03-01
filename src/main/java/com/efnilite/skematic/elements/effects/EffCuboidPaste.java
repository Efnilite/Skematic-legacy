package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.objects.PasteOptions;
import com.efnilite.skematic.objects.Schematic;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;

@Name("Paste cuboidregion")
@Description("Paste a cuboidregion at a location. This can be the player's selection, etc.")
@Examples("paste player's selection at player's location ignoring air and ignoring biomes and ignoring entities")
@Since("2.1")
public class EffCuboidPaste extends Effect {

    private Expression<CuboidRegion> cuboid;
    private Expression<Location> location;
    private Expression<PasteOptions> options;

    static {
        Skript.registerEffect(EffCuboidPaste.class, "paste %cuboidregions% at %location% [ignoring %-pasteoptions%]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        location = (Expression<Location>) expressions[1];
        options = (Expression<PasteOptions>) expressions[2];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        Location location = this.location.getSingle(e);
        PasteOptions[] options = this.options != null ? this.options.getArray(e) : new PasteOptions[] { PasteOptions.DEFAULT };

        if (cuboid == null || location == null) {
            return;
        }

        HashSet<PasteOptions> optionsSet = new HashSet<>(Arrays.asList(options));
        new Schematic(cuboid).paste(FaweUtils.getWorld(location.getWorld().getName()), FaweUtils.toVector(location), optionsSet);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste " + cuboid.toString(e, debug) + " at " + location.toString(e, debug) + (options != null ? " with options " + options.toString(e, debug) : "");
    }
}
