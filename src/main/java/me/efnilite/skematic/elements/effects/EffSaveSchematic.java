package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import me.efnilite.skematic.Skematic;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Name("Save schematic")
@Description("Create a new schematic.")
@Examples("save schematic selection of player to the file \"plugins/schematic.schematic\" with format schematic")
@Since("1.1.0")
public class EffSaveSchematic extends Effect {

    static {
        Skript.registerEffect(EffSaveSchematic.class,
                "save [[the] s(ch|k)ematic] %cuboidregions% to [[the] file] %string% [with %-schematicformat% format]");
    }

    private Expression<CuboidRegion> region;
    private Expression<String> schematic;
    private Expression<ClipboardFormat> format;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        region = (Expression<CuboidRegion>) exprs[0];
        schematic = (Expression<String>) exprs[1];
        format = (Expression<ClipboardFormat>) exprs[2];

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {

        CuboidRegion r = region.getSingle(e);
        String t = schematic.getSingle(e);

        if (t == null || r == null) {
            return;
        }

        ClipboardFormat cf = format.getSingle(e);

        if (cf == null) {
            cf = ClipboardFormat.SCHEMATIC;
        }

        Schematic s = new Schematic(r);

        try {
            s.save(new File(t), cf);
        } catch (IOException ex) {
            Skematic.log("Could not save schematic " + schematic.getSingle(e) + " with format " + cf, Level.SEVERE);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "create the schematic " + schematic.toString(e, debug) + ", with region " + region.toString(e, debug) + " with format " + format.toString(e, debug);
    }

}
