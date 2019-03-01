package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.objects.Schematic;
import com.efnilite.skematic.objects.SchematicLoader;
import org.bukkit.event.Event;

@Name("Last loaded schematic")
@Description("Get the lastly loaded schematic")
@Since("2.0")
public class ExprLastLoadedSchematic extends SimpleExpression<Schematic> {

    static {
        Skript.registerExpression(ExprLastLoadedSchematic.class, Schematic.class, ExpressionType.PROPERTY, "[the] last[ly] loaded s(k|ch)ematic");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    protected Schematic[] get(Event e) {
        return new Schematic[] { SchematicLoader.getLastLoaded() };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Schematic> getReturnType() {
        return Schematic.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "last loaded schematic";
    }
}
