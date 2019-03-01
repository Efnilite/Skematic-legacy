package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.example.NMSMappedFaweQueue;
import com.boydti.fawe.util.SetQueue;
import com.efnilite.skematic.utils.FaweUtils;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Light level")
@Description("Get the light level of a block.")
@Since("1.0")
public class ExprLightLevel extends SimpleExpression<Number> {

    private Expression<Location> location;

    static {
        Skript.registerExpression(ExprLightLevel.class, Number.class, ExpressionType.PROPERTY, "[the] [(skematic|fawe)] [block(-| )]light of [the] [block] (at|of) %location%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        location = (Expression<Location>) expressions[0];

        return true;
    }

    @Override
    protected Number[] get(Event e) {
        Location location = this.location.getSingle(e);

        if (location == null) {
            return null;
        }

        return new Number[] { FaweUtils.getWorld(location.getWorld().getName()).getBlockLightLevel(FaweUtils.toVector(location)) };
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(int[].class);
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            Location location = this.location.getSingle(e);

            if (location == null) {
                return;
            }

            NMSMappedFaweQueue queue = (NMSMappedFaweQueue) SetQueue.IMP.getNewQueue(FaweUtils.getWorld(location.getWorld().getName()), true, false);
            queue.setBlockLight(location.getBlockX(), location.getBlockY(), location.getBlockZ(), (int) delta[0]);
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "block light at " + location.toString(e, debug);
    }
}
