package me.efnilite.skematic.elements.effects.shapes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.FaweCache;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.efnilite.skematic.utils.FaweTools;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Event;

public class EffMakeCircle {/*extends Effect {

    static {
        Skript.registerEffect(EffMakeCircle.class, "(make|create) [a] [new] [(1¦hollow)] (sphere|circle) at %location% [(with|and)] [the] pattern %string% [(with|and)] radius %integer%");
    }

    private enum Fill {
        FILLED, HOLLOW
    }

    private Expression<Location> position;
    private Expression<String> pattern;
    private Expression<Integer> radius;
    private Fill fill;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {

        fill = Fill.values()[parser.mark];

        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void execute(Event e) {
        Location l = position.getSingle(e);
        String p = pattern.getSingle(e);
        Integer r = radius.getSingle(e);
        boolean f = true;

        if (l == null || p == null || r == null) {
            return;
        }

        switch (fill) {
            case HOLLOW:
                f = false;
            default:
                break;
        }

        EditSession session = FaweTools.getEditSession(Bukkit.getServer().getWorld(l.getWorld().getName()));
        session.makeSphere(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ()), new BlockPattern(new BaseBlock(BlockTypes.valueOf(p))), r, f);
        session.flushQueue();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "circle at ";
    }*/
}
