package com.efnilite.skematic.lang;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.lang.annotations.Return;
import com.efnilite.skematic.lang.annotations.Single;

public abstract class SkematicExpression<T> extends SimpleExpression {

    protected Expression<?>[] expressions;
    protected int mark;

    private static Class type;
    private static boolean single;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        if (exprs != null) {
            expressions = exprs;
        }


        mark = parseResult.mark;
        type = getReturnType();
        single = isSingle();

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class getReturnType() {
        if (getClass().getAnnotation(Return.class) != null) {
            return getClass().getAnnotation(Return.class).value();
        } else {
            throw new IllegalStateException("Return type not set");
        }
    }

    @Override
    public boolean isSingle() {
        return getClass().isAnnotationPresent(Single.class);
    }
}
