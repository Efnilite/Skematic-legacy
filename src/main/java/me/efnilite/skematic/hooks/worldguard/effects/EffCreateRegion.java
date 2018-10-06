package me.efnilite.skematic.hooks.worldguard.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import me.efnilite.skematic.Skematic;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import java.util.logging.Level;

public class EffCreateRegion extends Effect {

    static {
        Skript.registerEffect(EffCreateRegion.class, "create [the] [new] [worldguard] region [(named|called)] %string% with [the] [sel[ection]] of %player% [(with|and) world %world%]");
    }

    private Expression<Player> player;
    private Expression<World> world;
    private Expression<String> name;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        player = (Expression<Player>) exprs[1];
        if (exprs[2] != null) world = (Expression<World>) exprs[2];

        return true;
    }


    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create the new region called " + name.toString(event, debug) + " by player " + player.toString(event, debug) + " with world " + world.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name.toString(), new BlockVector(FawePlayer.wrap(player).getSelection().getMaximumPoint()), new BlockVector(FawePlayer.wrap(player).getSelection().getMinimumPoint()));
        DefaultDomain owners = new DefaultDomain();
        try {
            owners.addPlayer(WorldGuard.getWorldGuard().wrapPlayer(player.getSingle(e)));
        } catch (NullPointerException exception) {
            Skematic.log("Could not add " + player.toString() + " to the owners of region " + region.toString(), Level.SEVERE);
        }
        region.setOwners(owners);
        if (world == null) world = (Expression<World>) player.getSingle(e).getWorld();
        WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)).addRegion(region);
    }

}
