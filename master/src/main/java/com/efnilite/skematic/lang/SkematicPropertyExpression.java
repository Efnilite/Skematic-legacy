package com.efnilite.skematic.lang;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.lang.annotations.Return;

public abstract class SkematicPropertyExpression<F, T> extends SimplePropertyExpression<F, T> {

    protected Expression<?>[] expressions;
    protected int mark;

    private static Class type;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        if (exprs != null) {
            expressions = exprs;
        }

        mark = parseResult.mark;
        type = getReturnType();

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
}
