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
        Skript.registerEffect(EffCreateRegion.class, "create [the] [new] [worldguard] region [(named|called)] %string% with [the] (selection of %player%|%player%'s selection)]");
    }

    private Expression<Player> player;
    private Expression<String> name;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        player = (Expression<Player>) exprs[1];

        return true;
    }


    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create the new region called " + name.toString(event, debug) + " by player " + player.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        Player p = player.getSingle(e);
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name.getSingle(e), new BlockVector(FawePlayer.wrap(p).getSelection().getMaximumPoint()), new BlockVector(FawePlayer.wrap(p).getSelection().getMinimumPoint()));
        TaskManager.manager.async(() -> {
            DefaultDomain owners = new DefaultDomain();
            if (WorldGuard.getWorldGuard().wrapPlayer(p) != null) {
                owners.addPlayer(WorldGuard.getWorldGuard().wrapPlayer(p));
                region.setOwners(owners);
                WorldGuard.getWorldGuard().getRegionManager(Bukkit.getServer().getWorld(FaweAPI.wrapPlayer(p).getSession().getSelectionWorld().toString())).addRegion(region);
            }
        });
    }

}
