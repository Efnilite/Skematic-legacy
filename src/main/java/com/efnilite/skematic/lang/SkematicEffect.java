package com.efnilite.skematic.lang;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public abstract class SkematicEffect extends Effect {

    protected Expression<?>[] expressions;
    protected int mark;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        if (exprs != null) {
            expressions = exprs;
        }
        mark = parseResult.mark;

        return true;
    }

    protected Object getNullable(Event e, Expression<?> expression)  {
        if (expression != null && expression.getSingle(e) != null) {
            return expression.getSingle(e);
        } else {
            return null;
        }
    }

    protected Object getNullable(Event e, Expression<?> expression, Object def)  {
        if (expression != null && expression.getSingle(e) != null) {
            return expression.getSingle(e);
        } else {
            return def;
        }
    }
}
