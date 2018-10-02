package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprSelectionArea extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprSelectionArea.class, Number.class, ExpressionType.PROPERTY, "[the] (%player%'s selection area|selection area of %player%)");
    }

    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the selection of area " + player.toString(e, debug);
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
        return new Number[] { FaweAPI.wrapPlayer(player.toString()).getSelection().getArea() };
    }
}
