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

public class ExprRegionDimensions extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprRegionDimensions.class, Number.class, ExpressionType.PROPERTY, "[the] [skematic] (1¦length|2¦height|3¦width) of (%player%'s selection|selection of %player%)");
    }

    enum Dimension {
        LONG, HIGH, WIDE
    }

    private Expression<Player> player;
    private Dimension dimension;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        dimension = Dimension.values()[parseResult.mark];

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the dimensions of the selection of " + player.toString(e, debug);
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
        Number result = null;
        switch (dimension) {
            case LONG:
                result = FaweAPI.wrapPlayer(player.getSingle(e)).getSelection().getLength();
                break;
            case HIGH:
                result = FaweAPI.wrapPlayer(player.getSingle(e)).getSelection().getHeight();
                break;
            case WIDE:
                result = FaweAPI.wrapPlayer(player.getSingle(e)).getSelection().getWidth();
                break;
        } return new Number[] { result };
    }
}