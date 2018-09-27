package me.efnilite.skematic.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import me.efnilite.skematic.Skematic;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EffPasteSchematic extends Effect {

    private Expression<String> schematic;
    private Expression<Location> location;
    private Expression<Boolean> air;
    private Expression<Boolean> redo;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        schematic = (Expression<String>) exprs[0];
        location = (Expression<Location>) exprs[1];
        air = (Expression<Boolean>) exprs[2];
        redo = (Expression<Boolean>) exprs[3];

        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "paste the schematic " + schematic.toString(event, debug) + ", at location " + location.toString(event, debug) + " with air " + air.toString(event, debug) + " and with redo " + redo.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Skematic.getInstance().getServer().getScheduler().runTaskAsynchronously(Skematic.getInstance(), () -> {
            File schematic = new File(this.schematic.toString());
            if (!this.schematic.toString().endsWith(".schematic")) schematic = new File(this.schematic.toString() + ".schematic");
            try {
                FaweAPI.load(schematic).paste(new BukkitWorld(location.getSingle(event).getWorld()), new Vector(location.getSingle(event).getX(), location.getSingle(event).getY(), location.getSingle(event).getZ()), new Boolean(redo.toString()), new Boolean(air.toString()), null);
            } catch (FileNotFoundException exception) {
                Utilities.error("Could not find file '" + schematic + "'", exception, false);
            } catch (IOException exception) {
                Utilities.error("Could not paste schematic '" + schematic + "'", exception, true);
            }
        });
    }
}
