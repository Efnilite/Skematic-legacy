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
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.Skematic;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.DataException;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

@Name("Paste schematic")
@Description("Paste a schematic at a location with or without using air")
@Examples("paste skematic \"plugins/WorldEdit/skematic.schematic\" at player excluding air")
@Since("1.0.0")
public class EffPasteSchematic extends Effect {

    static {
        Skript.registerEffect(EffPasteSchematic.class, "paste [the] s(ch|k)em[atic] %string% at %location% [(1¦(without|excluding) air)] [(2¦[(,| and)] allow[ing] undo)] [[with] angle %-number%]");
    }
    
    private enum Subarg {
        NONE, AIR, UNDO, BOTH
    }

    private Expression<String> schematic;
    private Expression<Location> location;
    private Expression<Number> angle;

    private Subarg arg;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        arg = Subarg.values()[parser.mark];

        schematic = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        angle = (Expression<Number>) exprs[2];

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {

        String file = schematic.getSingle(e);
        Location l = location.getSingle(e);

        if (file == null || l == null) {
            return;
        }

        File s = new File(file);
        boolean ignoreAir = true;
        boolean allowUndo = false;
        switch (arg) {
            case AIR:
                ignoreAir = false;
                break;
            case UNDO:
                allowUndo = true;
                break;
            case BOTH:
                ignoreAir = true;
                allowUndo = true;
                break;
            case NONE:
                break;
        }
        if (!file.endsWith(".schematic")) {
            s = new File(file + ".schematic");
        }

        Vector vector = new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ());
        if (angle != null) {

            EditSession session = FaweAPI.getEditSessionBuilder(FaweTools.getWorld(l.getWorld().getName())).autoQueue(true).build();
            CuboidClipboard clipboard;
            try {
                clipboard = CuboidClipboard.loadSchematic(s);
            } catch (IOException | DataException ex) {
                return;
            }
            if (clipboard != null) {
                clipboard.rotate2D((int) angle.getSingle(e));
            }
            try {
                clipboard.paste(session, vector, ignoreAir);
            } catch (MaxChangedBlocksException exception) {
                Skematic.log("Could not paste schematic " + s, Level.SEVERE);
                return;
            }
            session.flushQueue();
        } else {
            try {
                FaweAPI.load(s).paste(FaweTools.getWorld(l.getWorld().getName()), vector, allowUndo, ignoreAir, null);
            } catch (IOException ex) {
                Skematic.log("Could not paste schematic " + s, Level.SEVERE);
            }
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "paste the schematic " + schematic.toString(e, debug) + ", at location " + location.toString(e, debug);
    }
}
