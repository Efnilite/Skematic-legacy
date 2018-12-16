package com.efnilite.skematic.lang;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.lang.annotations.Patterns;
import org.bukkit.event.Event;

public abstract class SkematicCondition extends Condition {

    protected Expression<?>[] expressions;
    protected int mark;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        if (exprs != null && getSyntax() != null) {
            expressions = exprs;
        }

        mark = parseResult.mark;

        return true;
    }

    private String[] getSyntax() {
        if (getClass().getAnnotation(Patterns.class) != null) {
            return getClass().getAnnotation(Patterns.class).value();
        } else {
            throw new IllegalAccessError("Pattern type not set");
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

}
