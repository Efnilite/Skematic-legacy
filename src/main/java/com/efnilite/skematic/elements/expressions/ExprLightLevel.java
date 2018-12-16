package com.efnilite.skematic.elements.expressions;

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
import com.boydti.fawe.example.NMSMappedFaweQueue;
import com.boydti.fawe.util.SetQueue;
import com.efnilite.skematic.lang.SkematicExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;
import com.efnilite.skematic.utils.FaweTools;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

@Name("Light level")
@Description("Get the light level of a block.")
@Examples("set {light} to the block light of the block at 2, 3, 4 in \"lightworld\"")
@Since("1.0.0")
@Patterns("[the] [block(-| )]light of [the] [block] (at|of) %location%")
@Return(Number.class)
@Single
public class ExprLightLevel extends SkematicExpression<Number> {

    static {
        Skript.registerExpression(ExprLightLevel.class, Number.class, ExpressionType.PROPERTY, "[the] [block(-| )]light of [the] [block] (at|of) %location%");
    }

    @Override
    protected Number[] get(Event e) {
        Location l = (Location) expressions[0].getSingle(e);

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
            Location l = (Location) expressions[0].getSingle(e);
            if (l == null) {
                return;
            }
            NMSMappedFaweQueue n = (NMSMappedFaweQueue) SetQueue.IMP.getNewQueue(FaweTools.getWorld(l.getWorld().getName()), true, false);
            n.setBlockLight(l.getBlockX(), l.getBlockY(), l.getBlockZ(), (int) delta[0]);
        }
    }

}
