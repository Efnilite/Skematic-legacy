package me.efnilite.skematic.hooks.worldguard.effects;

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
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import me.efnilite.skematic.util.TaskManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

@Name("Region - create")
@Description("Create a region.")
@Examples("create region \"newregion\" in \"world\"")
@Since("1.0.0")
public class EffCreateRegion extends Effect {

    static {
        Skript.registerEffect(EffCreateRegion.class, "create [the] [new] [worldguard] region [(named|called)] %string% with %cuboidregions%");
    }

    private Expression<CuboidRegion> region;
    private Expression<String> name;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        region = (Expression<CuboidRegion>) exprs[1];

        return true;
    }


    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create the new region called " + name.toString(event, debug) + " with region " + region.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {

        String m = name.getSingle(e);
        CuboidRegion r = region.getSingle(e);

        if (r == null || m == null) {
            return;
        }

        ProtectedCuboidRegion pcr = new ProtectedCuboidRegion(name.getSingle(e), new BlockVector(r.getMaximumPoint()), new BlockVector(r.getMinimumPoint()));
        TaskManager.manager.async(() ->
                WorldGuard.getWorldGuard().getRegionManager(Bukkit.getServer().getWorld(r.getWorld().getName())).addRegion(pcr)
        );
    }

}
