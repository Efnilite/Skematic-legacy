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
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import me.efnilite.skematic.Skematic;
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
        Skript.registerEffect(EffPasteSchematic.class, "paste [the] s(ch|k)em[atic] %schematic% at %location% [(1¦(without|excluding) air)] [(2¦[(,| and)] allow[ing] undo)]");
    }
    enum Subarg {
        NONE, AIR, UNDO, BOTH
    }

    private Expression<File> schematic;
    private Expression<Location> location;

    private Subarg arg;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        arg = Subarg.values()[parser.mark];

        schematic = (Expression<File>) exprs[0];
        location = (Expression<Location>) exprs[1];

        return true;
    }

    @Override
    protected void execute(Event e) {

        File s = schematic.getSingle(e);
        Location l = location.getSingle(e);

        if (s == null || l == null) {
            return;
        }

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

        if (!s.toString().endsWith(".schematic")) {
            s = new File(s + ".schematic");
        }

        try {
            FaweAPI.load(s).paste(BukkitUtil.getLocalWorld(l.getWorld()), new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()), allowUndo, ignoreAir, null);
        } catch (IOException ex) {
            Skematic.log("Could not paste schematic " + s + ". Exception: " + ex.toString(), Level.SEVERE);
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "paste the schematic " + schematic.toString(e, debug) + ", at location " + location.toString(e, debug);
    }
}
