package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.example.NMSMappedFaweQueue;
import com.boydti.fawe.util.SetQueue;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.efnilite.skematic.utils.FaweUtils;
import org.bukkit.Location;
import org.bukkit.event.Event;

@Name("Light level")
@Description("Get the light level of a block.")
@Return(Number.class)
@Single
public class ExprLightLevel extends SkematicExpression<Number> {

    static {
        Skript.registerExpression(ExprLightLevel.class, Number.class, ExpressionType.PROPERTY, "[the] [(skematic|fawe)] [block(-| )]light of [the] [block] (at|of) %location%");
    }

    @Override
    protected Number[] get(Event e) {
        Location location = (Location) expressions[0].getSingle(e);

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
            Location location = (Location) expressions[0].getSingle(e);

            if (location == null) {
                return;
            }

            NMSMappedFaweQueue queue = (NMSMappedFaweQueue) SetQueue.IMP.getNewQueue(FaweUtils.getWorld(location.getWorld().getName()), true, false);
            queue.setBlockLight(location.getBlockX(), location.getBlockY(), location.getBlockZ(), (int) delta[0]);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "block light at " + expressions[0].toString(e, debug);
    }
}
