package me.efnilite.skematic.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import me.efnilite.skematic.util.Utilities;
import me.efnilite.skematic.util.WorldGuardHook;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;



public class EffCreateRegion extends Effect {


    private Expression<Player> player;
    private Expression<String> name;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        name = (Expression<String>) exprs[0];
        player = (Expression<Player>) exprs[1];

        return false;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "create the new region called " + name.toString(event, debug) + " by player " + player.toString(event, debug);
    }

    @Override
    protected void execute(Event e) {
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(name.toString(), new BlockVector(FawePlayer.wrap(player).getSelection().getMaximumPoint()), new BlockVector(FawePlayer.wrap(player).getSelection().getMinimumPoint()));
        DefaultDomain owners = new DefaultDomain();
        try {
            owners.addPlayer(WorldGuardHook.getWorldGuard().wrapPlayer(player.getSingle(e)));
        } catch (NullPointerException exception) {
            Utilities.error("Could not add player to the owners of region " + region.toString(), exception, false);
        }
        region.setOwners(owners);
        WorldGuardHook.getWorldGuard().getRegionManager(player.getSingle(e).getWorld()).addRegion(region);


    }

}
