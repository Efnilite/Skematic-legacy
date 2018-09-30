package me.efnilite.skematic.hooks.worldguard.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprRegionOwners extends SimpleExpression<Player> {

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
    public Class getReturnType() {
        return null;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "set or remove " + player.toString(e, debug) + " of region " + name.toString(e, debug) + " in world " + world.toString(e, debug);
    }

    @Override
    protected Player[] get(Event e) {
        DefaultDomain player = null;
        try {
            player = WorldGuard.getWorldGuard().getRegionManager(world.getSingle(e)).getRegion(name.getSingle(e)).getOwners();
        } catch (NullPointerException exception) {
            Utilities.error("There aren't any owners of region " + name.toString(), exception, false);
        }
        return new Player[] { (Player) player };
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
            return CollectionUtils.array(Player[].class);
        }
        return null;
    }

    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {

        ProtectedRegion region;
        DefaultDomain owners = new DefaultDomain();

        if (mode == Changer.ChangeMode.REMOVE) {
            try {
                region = WorldGuard.getWorldGuard().getRegionManager(world.getSingle(event)).getRegion(name.getSingle(event));
                if (region.getOwners() != null) for (String owner : region.getOwners().getPlayers()) {
                    owners.addPlayer(owner);
                }
                owners.removePlayer(player.toString());
                region.setOwners(owners);
            } catch (NullPointerException exception) {
                Utilities.error("Could not add" + player.toString() + " to owners of region " + name.toString(), exception, false);
            }
        } else if (mode == Changer.ChangeMode.ADD) {
            try {
                region = WorldGuard.getWorldGuard().getRegionManager(world.getSingle(event)).getRegion(name.getSingle(event));
                if (region.getOwners() != null) for (String owner : region.getOwners().getPlayers()) {
                    owners.addPlayer(owner);
                }
                owners.addPlayer(player.toString());
                region.setOwners(owners);
            } catch (NullPointerException exception) {
                Utilities.error("Could not add" + player.toString() + " to owners of region " + name.toString(), exception, false);
            }
        }
    }
}
