package me.efnilite.skematic.elements.hooks.worldguard.effects;

import ch.njol.skript.hooks.regions.WorldGuardHook;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.efnilite.skematic.elements.hooks.worldguard.WorldGuard;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffAddOwner extends Effect {

    private Expression<String> name;
    private Expression<World> world;
    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];
        name = (Expression<String>) exprs[1];
        world = (Expression<World>) exprs[2];

        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    protected void execute(Event e) {
        ProtectedRegion region;
        DefaultDomain owners = new DefaultDomain();
        try {
            region = WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)).getRegion(name.getSingle(e));
            owners.addPlayer(player.toString());
            if (region.getOwners() != null) for (String owner : region.getOwners().getPlayers()) {
                owners.addPlayer(owner);
            }
            region.setOwners(owners);
        } catch (NullPointerException exception) {
            Utilities.error("Could not add" + player.toString() + " to owners of region " + name.toString(), exception, false);
        }
    }
}
