package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.objects.SchematicLoader;
import org.bukkit.event.Event;

@Name("Unload schematic")
@Description("Unload a saved schematic")
@Examples("unload skematic \"loaded-schematic\"")
@Since("2.1")
public class EffSchematicUnload extends AsyncEffect {

    private Expression<String> schematic;

    static {
        Skript.registerEffect(EffSchematicUnload.class, "unload s(k|ch)em[atic] %strings%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = (Expression<String>) expressions[0];

        return true;
    }

    @Override
    protected void execute(Event e) {
        String schematic = this.schematic.getSingle(e);

        if (schematic == null) {
            return;
        }

        SchematicLoader.remove(schematic);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "load " + schematic.toString(e, debug);
    }
}
