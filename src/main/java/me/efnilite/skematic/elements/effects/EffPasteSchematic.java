package me.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
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

public class EffPasteSchematic extends Effect {

    static {
        Skript.registerEffect(EffPasteSchematic.class, "paste [a] [new] schem[atic] %string% at %location% in %world% [(1¦(without|excluding) air)] [(2¦[(,| and)] allow[ing] undo)]");
    }

    enum Subarg {
        AIR, UNDO, BOTH
    }

    private Expression<String> schematic;
    private Expression<Location> location;
    private Expression<org.bukkit.World> world;
    private Expression<Boolean> air;
    private Expression<Boolean> redo;

    private Subarg arg;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        arg = Subarg.values()[parser.mark];

        schematic = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        world = (Expression<org.bukkit.World>) exprs[2];
        air = (Expression<Boolean>) exprs[3];
        redo = (Expression<Boolean>) exprs[4];

        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "paste the schematic " + schematic.toString(event, debug) + ", at location " + location.toString(event, debug) + " with air " + air.toString(event, debug) + " and with redo " + redo.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {

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
        }

        String s = schematic.getSingle(e).replace("\"", "");
        Location l = location.getSingle(e);

        if (!s.endsWith(".schematic")) {
            s = s + ".schematic";
        }

        try {
            FaweAPI.load(new File(s)).paste(BukkitUtil.getLocalWorld(world.getSingle(e)), new Vector(l.getBlockX(), l.getBlockY(),l.getBlockZ()), allowUndo, ignoreAir, null);
        } catch (IOException ex) {
            Skematic.log("Could not paste schematic " + schematic.getSingle(e) + ". Exception: " + ex.toString(), Level.SEVERE);
        }
    }
}
