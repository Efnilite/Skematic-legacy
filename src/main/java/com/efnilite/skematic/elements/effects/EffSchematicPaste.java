package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.Skematic;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Name("Paste schematic")
@Description("Paste a schematic at a location with or without using air")
@Examples("paste skematic \"plugins/WorldEdit/skematic.schematic\" at player excluding air")
public class EffSchematicPaste extends SkematicEffect {

    static {
        Skript.registerEffect(EffSchematicPaste.class, "paste [the] s(ch|k)em[atic] %skematics% at %location% [(1¦ignoring air)] [(2¦[(,| and)] allow[ing] undo)] [[with] angle %-number%]");
    }

    @Override
    @SuppressWarnings({"deprecation", "ConstantConditions"})
    protected void execute(Event e) {
        File schematic = (File) expressions[0].getSingle(e);
        Location location = (Location) expressions[1].getSingle(e);
        Number angle = (Number) getNullable(e, expressions[2]);
        boolean ignoreAir = false;
        boolean allowUndo = false;

        if (schematic == null || location == null) {
            return;
        }

        if ((mark & 1) == 1) {
            ignoreAir = true;
        }
        if ((mark & 2) == 2) {
            allowUndo = true;
        }

        switch (mark) {
            case 1:
                ignoreAir = false;
                break;
            case 2:
                allowUndo = true;
                break;
            case 3:
                ignoreAir = true;
                allowUndo = true;
                break;
            case 0:
                break;
        }

        if (!schematic.toString().endsWith(".schematic")) {
            schematic = new File(schematic + ".schematic");
        }

        Vector vector = FaweUtils.toVector(location);
        if (angle != null) {

            EditSession session = FaweUtils.getEditSession(location.getWorld());
            CuboidClipboard clipboard;
            try {
                clipboard = CuboidClipboard.loadSchematic(schematic);
            } catch (IOException | DataException ex) {
                return;
            }
            clipboard.rotate2D((int) angle);
            try {
                clipboard.paste(session, vector, ignoreAir);
            } catch (MaxChangedBlocksException exception) {
                Skematic.log("Could not paste schematic " + schematic, Level.SEVERE);
                return;
            }
            session.flushQueue();
        } else {
            FaweUtils.toSchematic(schematic).paste(FaweUtils.getWorld(location.getWorld().getName()), vector, allowUndo, ignoreAir, null);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste schematic " + expressions[0].toString(e, debug) + " at " + expressions[1].toString(e, debug) + (expressions[3] != null ? " with angle " + expressions[3].toString(e, debug) : "");
    }
}
