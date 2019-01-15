package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.object.FawePlayer;
import com.efnilite.skematic.lang.SkematicEffect;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Paste cuboidregion")
@Description("Paste a cuboidregion at a location. This can be the player's selection, etc.")
@Examples("paste player's selection at player's location ignoring air and ignoring biomes and ignoring entities")
public class EffCuboidPaste extends SkematicEffect {

    static {
        Skript.registerEffect(EffCuboidPaste.class, "paste %cuboidregions% at %location% [(1¦(and|with) ignoring air)] [(2¦(and|with) ignoring biomes)] [(4¦(and|with) ignoring (entities|mobs|animals))]");
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void execute(Event e) {
        CuboidRegion cuboid = (CuboidRegion) expressions[0].getSingle(e);
        Location location = (Location) expressions[1].getSingle(e);
        boolean ignoreAir = false;
        boolean ignoreBiome = false;
        boolean ignoreEntity = false;

        if (cuboid == null || location == null) {
            return;
        }

        if ((mark & 1) == 1) {
            ignoreAir = true;
        }
        if ((mark & 2) == 2) {
            ignoreBiome = true;
        }
        if ((mark & 4) == 4) {
            ignoreEntity = true;
        }

        EditSession session = FaweUtils.getEditSession(Bukkit.getWorld(cuboid.getWorld().getName()));
        FawePlayer player = session.getPlayer();
        ClipboardHolder holder = player.getSession().getClipboard();

        Operation operation = holder
                .createPaste(session)
                .to(FaweUtils.toVector(location))
                .ignoreAirBlocks(ignoreAir)
                .ignoreBiomes(ignoreBiome)
                .ignoreEntities(ignoreEntity)
                .build();
        Operations.completeLegacy(operation);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "paste " + expressions[0].toString(e, debug) + " at " + expressions[1].toString(e, debug);
    }
}
