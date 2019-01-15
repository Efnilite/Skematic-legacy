package com.efnilite.skematic.lang;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.lang.annotations.Return;
import org.bukkit.event.Event;

public abstract class SkematicPropertyExpression<F, T> extends SimplePropertyExpression<F, T> {

    protected Expression<?>[] expressions;
    protected int mark;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        if (exprs != null) {
            expressions = exprs;
        }

        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public Class getReturnType() {
        if (getClass().getAnnotation(Return.class) != null) {
            return getClass().getAnnotation(Return.class).value();
        } else {
            throw new IllegalAccessError("Return type not set");
        }
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
