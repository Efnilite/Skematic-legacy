package me.efnilite.skematic.elements.effects.shapes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.util.TreeGenerator;
import org.bukkit.Location;
import org.bukkit.event.Event;

public class EffForest extends Effect {

    static {
        Skript.registerEffect(EffForest.class, "(draw|create|make) [a] [new] [(fawe|fastasyncworldedit)] forest with (radius|size) %integer%[(,| and)] with [the] density %number%[(,| and)] [using] tree[[(-| )]type] %string% at %location% using [the] (%player%'s edit(-| )session|edit(-| )session of %player%)");
    }

    private Expression<Integer> size;
    private Expression<Number> density;
    private Expression<String> tree;
    private Expression<Location> position;
    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        size = (Expression<Integer>) exprs[0];
        density = (Expression<Number>) exprs[1];
        tree = (Expression<String>) exprs[2];
        position = (Expression<Location>) exprs[3];
        player = (Expression<Player>) exprs[4];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "draw a new forest at " + position.toString(e, debug) + " with size " + size.toString(e, debug) + " with density " + density.toString(e, debug) + " using treetype " + tree.toString(e, debug) + " using the editession of " + player.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        TreeGenerator.TreeType type = TreeGenerator.TreeType.lookup(tree.getSingle(e));

        FaweAPI.wrapPlayer(player.toString()).getNewEditSession().makeForest(new Vector(position.getSingle(e).getBlockX(), position.getSingle(e).getBlockY(), position.getSingle(e).getBlockZ()), size.getSingle(e), (double) density.getSingle(e), type);
    }
}
