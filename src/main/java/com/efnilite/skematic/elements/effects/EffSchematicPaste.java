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
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Name("Paste schematic")
@Description("Paste a schematic at a location with or without using air")
@Examples("paste skematic \"plugins/WorldEdit/skematic.schematic\" at player excluding air")
@Since("1.0")
public class EffSchematicPaste extends Effect {

    private Expression<Schematic> schematic;
    private Expression<Location> location;
    private Expression<PasteOptions> options;
    private Expression<Number> angle;

    static {
        Skript.registerEffect(EffSchematicPaste.class, "paste [the] s(ch|k)em[atic] %schematics% at %location% [ignoring %-pasteoptions%] [[with] angle %-number%]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = (Expression<Schematic>) expressions[0];
        location = (Expression<Location>) expressions[1];
        options = (Expression<PasteOptions>) expressions[2];
        angle = (Expression<Number>) expressions[3];

        return true;
    }

    @Override
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    protected void execute(Event e) {
        Schematic schematic = this.schematic.getSingle(e);
        Location location = this.location.getSingle(e);
        PasteOptions[] options = this.options != null ? this.options.getArray(e) : new PasteOptions[] { PasteOptions.DEFAULT };
        Number angle = this.angle != null ? this.angle.getSingle(e) : null;

        if (schematic == null || location == null || options == null) {
            return;
        }

        Set<PasteOptions> optionsSet = new HashSet<>();

        if (options == null) {
            optionsSet.addAll(Arrays.asList(options));
        }

        Vector vector = FaweUtils.toVector(location);
        if (angle != null) {

            EditSession session = FaweUtils.getEditSession(location.getWorld());
            CuboidClipboard clipboard;
            try {
                clipboard = CuboidClipboard.loadSchematic(schematic.getFile());
            } catch (IOException | DataException ex) {
                return;
            }
            clipboard.rotate2D((int) angle);
            try {
                clipboard.paste(session, vector, !optionsSet.contains(PasteOptions.AIR));
            } catch (MaxChangedBlocksException exception) {
                return;
            }
            session.flushQueue();
        } else {
            schematic.paste(FaweUtils.getWorld(location.getWorld().getName()), vector, optionsSet);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste schematic " + schematic.toString(e, debug) + " at " + location.toString(e, debug) + (angle != null ? " with angle " + angle.toString(e, debug) : "");
    }
}
