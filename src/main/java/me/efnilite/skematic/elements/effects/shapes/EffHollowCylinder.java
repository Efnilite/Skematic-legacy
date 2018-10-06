package me.efnilite.skematic.elements.effects.shapes;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandLocals;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.command.argument.PatternParser;
import com.sk89q.worldedit.function.pattern.FawePattern;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.command.argument.CommandArgs;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;

public class EffHollowCylinder extends Effect {

    static {
        Skript.registerEffect(EffHollowCylinder.class, "(draw|create|make) [a] [new] [(fawe|fastasyncworldedit)] (hollow|empty) cylinder with radius %integer%[(,| and)] [with] [wall] thickness %integer%[(,| and)] [with] [the] pattern %string% at %location% using [the] (%player%'s edit(-| )session|edit(-| )session of %player%)");
    }

    private Expression<Player> player;
    private Expression<Location> position;
    private Expression<String> pattern;
    private Expression<Integer> size;
    private Expression<Integer> height;
    private Expression<Integer> thickness;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        size = (Expression<Integer>) exprs[0];
        height = (Expression<Integer>) exprs[1];
        pattern = (Expression<String>) exprs[2];
        thickness = (Expression<Integer>) exprs[3];
        position = (Expression<Location>) exprs[4];
        player = (Expression<Player>) exprs[5];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make a new cylinder at " + position.toString(e, debug) + " with radius " + size.toString(e, debug) + " with height " + height.toString(e, debug) + " with the pattern " + pattern.toString(e, debug) + " using the editsession of " + player.toString(e, debug) + " and with filled being " + thickness.toString(e, debug);
    }

    @Override
    protected void execute(Event e) {
        ArrayList<String> list = new ArrayList<>();
        list.add(pattern.toString());

        CommandLocals locals = new CommandLocals();
        locals.put(FawePattern.class, pattern.toString());

        CommandArgs args = new CommandArgs(list);
        try {
            FaweAPI.wrapPlayer(player.toString()).getNewEditSession().makeHollowCylinder(new Vector(position.getSingle(e).getBlockX(), position.getSingle(e).getBlockY(), position.getSingle(e).getBlockZ()), new PatternParser(pattern.getSingle(e)).call(args, locals), size.getSingle(e), size.getSingle(e), height.getSingle(e), thickness.getSingle(e));
        } catch (CommandException exception) {
            exception.printStackTrace();
        }

    }

}
