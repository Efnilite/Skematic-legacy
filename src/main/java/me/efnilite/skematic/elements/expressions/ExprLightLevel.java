package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.example.NMSMappedFaweQueue;
import com.boydti.fawe.jnbt.anvil.MCAQueue;
import com.boydti.fawe.object.FaweChunk;
import com.boydti.fawe.object.FaweQueue;
import com.boydti.fawe.util.SetQueue;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;

@Name("Light level")
@Description("Get the light level of a block.")
@Examples("set {light} to the block light of the block at 2, 3, 4 in \"lightworld\"")
@Since("1.0.0")
public class ExprLightLevel extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprLightLevel.class, Number.class, ExpressionType.PROPERTY, "[the] [block(-| )]light of [the] [block] (at|of) %location%");
    }

    private Expression<World> world;
    private Expression<Location> location;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        world = (Expression<World>) exprs[1];
        location = (Expression<Location>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "light level of block at " + location.toString(e, debug) + " in world " + world.toString(e, debug);
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
    protected Number[] get(Event e) {
        Location pos = location.getSingle(e);


        FaweQueue defaultQueue = SetQueue.IMP.getNewQueue(BukkitUtil.getLocalWorld(world.getSingle(e)), true, false);
        MCAQueue mcaQueue = new MCAQueue(defaultQueue);
        mcaQueue.setBlockLight(1, 1, 1, 1);

        NMSMappedFaweQueue nmsQueue = (NMSMappedFaweQueue) SetQueue.IMP.getNewQueue("worldName", true, false);

        return new Number[] { FaweAPI.getWorld(world.getSingle(e).getName()).getBlockLightLevel(new Vector(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ())) };
    }
}
