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
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.schematic.SchematicFormat;
import com.sk89q.worldedit.util.Direction;
import com.sk89q.worldedit.world.DataException;
import me.efnilite.skematic.Skematic;
import org.bukkit.Bukkit;
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
        Skript.registerEffect(EffPasteSchematic.class, "paste [the] s(ch|k)em[atic] %string% at %location% [(1¦(without|excluding) air)] [(2¦[(,| and)] allow[ing] undo)] [[with] angle %-integer%]");
    }
    enum Subarg {
        NONE, AIR, UNDO, BOTH
    }

    private Expression<String> schematic;
    private Expression<Location> location;
    private Expression<Integer> angle;

    private Subarg arg;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        arg = Subarg.values()[parser.mark];

        schematic = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        angle = (Expression<Integer>) exprs[2];

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
        boolean ignoreAir = false;
        boolean allowUndo = false;
        switch (arg) {
            case AIR:
                ignoreAir = true;
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

            System.out.println("old");

            EditSession session = FaweAPI.getEditSessionBuilder(BukkitUtil.getLocalWorld(l.getWorld())).autoQueue(true).build();
            CuboidClipboard clipboard;
            try {
                clipboard = SchematicFormat.getFormat(s).load(s);
            } catch (IOException | DataException ex) {
                return;
            }
            if (clipboard != null) {
                clipboard.rotate2D(angle.getSingle(e));
            }
            clipboard.paste(session, vector, ignoreAir);
            session.flushQueue();
        } else {
            try {
                FaweAPI.load(s).paste(BukkitUtil.getLocalWorld(l.getWorld()), vector, allowUndo, ignoreAir, null);
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
