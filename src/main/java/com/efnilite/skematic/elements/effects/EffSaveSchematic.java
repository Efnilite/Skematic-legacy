package com.efnilite.skematic.elements.effects;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.object.schematic.Schematic;
import com.efnilite.skematic.Skematic;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Name("Save schematic")
@Description("Create a new schematic.")
@Examples("save schematic selection of player to the file \"plugins/schematic.schematic\" with format schematic")
@Patterns("save [[the] s(ch|k)ematic] %cuboidregions% to [[the] file] %string% [with %-schematicformat% format]")
public class EffSaveSchematic extends SkematicEffect {

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        String schematic = (String) expressions[1].getSingle(e);
        ClipboardFormat format = (ClipboardFormat) expressions[2].getSingle(e);

        if (cuboid == null || schematic == null) {
            return;
        }

        if (format == null) {
            format = ClipboardFormat.SCHEMATIC;
        }

        Schematic file = new Schematic(cuboid);

        try {
            file.save(new File(schematic), format);
        } catch (IOException ex) {
            Skematic.log("Could not save schematic " + schematic + " with format " + format, Level.SEVERE);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "save schematic " + expressions[1].toString(e, debug) + " with region " + expressions[0].toString(e, debug) + (expressions[2] != null ? " with format " + expressions[2].toString(e, debug) : "");
    }
}
