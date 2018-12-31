package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.util.Direction;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.Skematic;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweTools;
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
public class EffPasteSchematic extends SkematicEffect {

    static {
        Skript.registerEffect(EffPasteSchematic.class, "paste [the] s(ch|k)em[atic] %string% %direction% %location% [(1¦(without|excluding) air)] [(2¦[(,| and)] allow[ing] undo)] [[with] angle %-number%]");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        String schematic = (String) expressions[0].getSingle(e);
        Location location = Direction.combine((Expression<Direction>) expressions[1], (Expression<Location>) expressions[2]).getSingle(e);
        Number angle = (Number) expressions[3].getSingle(e);

        if (schematic == null) {
            return;
        }

        File file = new File(schematic);
        boolean ignoreAir = true;
        boolean allowUndo = false;
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
        if (!schematic.endsWith(".schematic")) {
            file = new File(schematic + ".schematic");
        }

        Vector vector = FaweTools.toVector(location);
        if (angle != null) {

            EditSession session = FaweTools.getEditSession(location.getWorld());
            CuboidClipboard clipboard;
            try {
                clipboard = CuboidClipboard.loadSchematic(file);
            } catch (IOException | DataException ex) {
                return;
            }
            if (clipboard != null) {
                clipboard.rotate2D((int) angle);
            }
            try {
                clipboard.paste(session, vector, ignoreAir);
            } catch (MaxChangedBlocksException exception) {
                Skematic.log("Could not paste schematic " + file, Level.SEVERE);
                return;
            }
            session.flushQueue();
        } else {
            try {
                FaweAPI.load(file).paste(FaweTools.getWorld(location.getWorld().getName()), vector, allowUndo, ignoreAir, null);
            } catch (IOException ex) {
                Skematic.log("Could not paste schematic " + file, Level.SEVERE);
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste schematic " + expressions[0].toString(e, debug) + " at " + expressions[1].toString(e, debug) + (expressions[3] != null ? " with angle " + expressions[3].toString(e, debug) : "");
    }
}
