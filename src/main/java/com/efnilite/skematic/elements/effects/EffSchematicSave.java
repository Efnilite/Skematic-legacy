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
import com.efnilite.skematic.objects.Schematic;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.event.Event;

import java.io.File;

@Name("Save schematic")
@Description("Create a new schematic.")
@Examples("save schematic selection of player to the file \"plugins/schematic.schematic\" with schematic format")
@Since("2.0")
public class EffSchematicSave extends Effect {

    private Expression<CuboidRegion> cuboid;
    private Expression<String> file;
    private Expression<BuiltInClipboardFormat> format;

    static {
        Skript.registerEffect(EffSchematicSave.class, "save [[the] s(ch|k)ematic] %cuboidregions% to [[the] file] %string% [with %-schematicformat% format]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        cuboid = (Expression<CuboidRegion>) expressions[0];
        file = (Expression<String>) expressions[1];
        format = (Expression<BuiltInClipboardFormat>) expressions[2];

        return true;
    }

    @Override
    protected void execute(Event e) {
        CuboidRegion cuboid = this.cuboid.getSingle(e);
        String schematic = this.file.getSingle(e);
        BuiltInClipboardFormat format = this.format != null ? this.format.getSingle(e) : BuiltInClipboardFormat.SPONGE_SCHEMATIC;

        if (cuboid == null || schematic == null || format == null) {
            return;
        }

        new Schematic(cuboid).save(new File(schematic), format);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "save schematic " + file.toString(e, debug) + " with region " + cuboid.toString(e, debug) + (format != null ? " with format " + format.toString(e, debug) : "");
    }
}
