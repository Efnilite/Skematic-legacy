package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;

@Name("Paste schematic")
@Description("Paste a schematic at a location with or without using air")
@Examples("paste skematic \"plugins/WorldEdit/schematics/skematic.schematic\" at player excluding air")
public class EffPasteSchematic extends SkematicEffect {

    static {
        Skript.registerEffect(EffPasteSchematic.class, "paste [the] s(ch|k)em[atic] %string% at %location% [(1¦(without|excluding) air)] [(2¦[(,| and)] allow[ing] undo)] [[with] angle %-number%]");
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        String schematic = (String) expressions[0].getSingle(e);
        Location location = (Location) expressions[1].getSingle(e);
        Number angle = (Number) getNullable(e, expressions[2]);

        System.out.println(angle);

        if (schematic == null || location == null) {
            return;
        }

        File file = new File(schematic.endsWith(".schematic") ? schematic : schematic + ".schematic");
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

        Vector vector = FaweUtils.toVector(location);
        if (angle != null) {

            EditSession session = FaweUtils.getEditSession(location.getWorld());
            CuboidClipboard clipboard;
            try {
                clipboard = CuboidClipboard.loadSchematic(file);
            } catch (IOException | DataException ex) {
                return;
            }

            try {
                clipboard.paste(session, vector, ignoreAir);
            } catch (MaxChangedBlocksException exception) {
                Skript.error("[Skematic] Could not paste schematic " + file);
                return;
            }
            session.flushQueue();
        } else {
            try {
                FaweAPI.load(file).paste(FaweUtils.getWorld(location.getWorld().getName()), vector, allowUndo, ignoreAir, null);
            } catch (IOException ex) {
                Skript.error("[Skematic] Could not paste schematic " + file);
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste schematic " + expressions[0].toString(e, debug) + " at " + expressions[1].toString(e, debug) + (expressions[3] != null ? " with angle " + expressions[3].toString(e, debug) : "");
    }
}
