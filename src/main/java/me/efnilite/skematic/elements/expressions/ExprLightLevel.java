package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.example.NMSMappedFaweQueue;
import com.boydti.fawe.util.SetQueue;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import me.efnilite.skematic.utils.FaweTools;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Light level")
@Description("Get the light level of a block.")
@Examples("set {light} to the block light of the block at 2, 3, 4 in \"lightworld\"")
@Since("1.0.0")
public class ExprLightLevel extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprLightLevel.class, Number.class, ExpressionType.PROPERTY, "[the] [block(-| )]light of [the] [block] (at|of) %location%");
    }

    private Expression<Location> location;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        location = (Expression<Location>) exprs[0];

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        Location l = location.getSingle(e);

        if (l == null) {
            return null;
        }

        return new Number[] { FaweTools.getWorld(l.getWorld().getName()).getBlockLightLevel(new Vector(l.getBlockX(), l.getBlockY(), l.getBlockZ())) };
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Player[].class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {

            Location l = location.getSingle(e);

            if (l == null) {
                return;
            }

            NMSMappedFaweQueue n = (NMSMappedFaweQueue) SetQueue.IMP.getNewQueue(FaweTools.getWorld(l.getWorld().getName()), true, false);
            n.setBlockLight(l.getBlockX(), l.getBlockY(), l.getBlockZ(), (int) delta[0]);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "light level at " + location.toString(e, debug);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

}
