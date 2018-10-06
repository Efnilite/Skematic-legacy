package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.Vector;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprSelectionCorners extends SimpleExpression<Vector> {

    static {
        Skript.registerExpression(ExprSelectionCorners.class, Vector.class, ExpressionType.PROPERTY, "[the] [skematic] (1¦min[imum]|2¦max[imum]) (coord[inate]|point[s]) of (%player%'s selection area|selection area of %player%)");
    }

    private Expression<Player> player;
    private Integer mark;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        mark = parseResult.mark;

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the min and max points of the selection of " + player.toString(e, debug) ;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Vector> getReturnType() {
        return Vector.class;
    }

    @Override
    protected Vector[] get(Event e) {
        Vector vector = null;
        switch (mark) {
            case 1:
                vector = FaweAPI.wrapPlayer(player.toString()).getSelection().getMinimumPoint();
                break;
            case 2:
                vector = FaweAPI.wrapPlayer(player.toString()).getSelection().getMaximumPoint();
                break;
        } return new Vector[] { vector };
    }

}
