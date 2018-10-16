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
import com.sk89q.worldedit.regions.Region;
import me.efnilite.skematic.Skematic;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Name("Save schematic")
@Description("Create a new schematic.")
@Examples("set {region} to a new region between location 0, 2, 3 in \"world\" and location 4, 3, 7 in \"world\"")
@Since("1.1.0")
public class EffSchematicSave extends Effect {

    static {
        Skript.registerEffect(EffSchematicSave.class,
                "save [the] [new] s(k|ch)em[atic] %string% (with|using) [(cuboid|worldedit|we)region] %weregions% [and] [with] format %string%");
    }

    private Expression<String> schematic;
    private Expression<Region> region;
    private Expression<String> format;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        schematic = (Expression<String>) exprs[0];
        region = (Expression<Region>) exprs[1];
        format = (Expression<String>) exprs[2];

        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "create the schematic " + schematic.toString(e, debug) + ", with region " + region.toString(e, debug) + " with format " + format.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {

        Region r = region.getSingle(e);
        String t = schematic.getSingle(e);
        String cf = format.getSingle(e);

        if (t == null || r == null || cf == null) {
            return;
        }

        File f = new File(t);
        BlockArrayClipboard c = r.getWorld().lazyCopy(r);
        Schematic s = new Schematic(c);

        try {
            s.save(f, ClipboardFormat.findByAlias(cf));
        } catch (IOException ex) {
            Skematic.log("Could not save schematic " + schematic.getSingle(e) + " with format " + ClipboardFormat.findByAlias(format.getSingle(e)).toString(), Level.SEVERE);
        }
    }

}
