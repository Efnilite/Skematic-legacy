package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.example.NMSMappedFaweQueue;
import com.boydti.fawe.util.SetQueue;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.efnilite.skematic.utils.FaweTools;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Name("Light level")
@Description("Get the light level of a block.")
@Patterns("[the] [(skematic|fawe)] [block(-| )]light of [the] [block] (at|of) %location%")
@Return(Number.class)
@Single
public class ExprLightLevel extends SkematicExpression<Number> {

    @Override
    protected Number[] get(Event e) {
        Location location = (Location) expressions[0].getSingle(e);

        if (location == null) {
            return null;
        }

        return new Number[] { FaweTools.getWorld(location.getWorld().getName()).getBlockLightLevel(FaweTools.toVector(location)) };
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
            Location l = (Location) expressions[0].getSingle(e);
            if (l == null) {
                return;
            }
            NMSMappedFaweQueue n = (NMSMappedFaweQueue) SetQueue.IMP.getNewQueue(FaweTools.getWorld(l.getWorld().getName()), true, false);
            n.setBlockLight(l.getBlockX(), l.getBlockY(), l.getBlockZ(), (int) delta[0]);
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "block light at " + expressions[0].toString(e, debug);
    }
}
