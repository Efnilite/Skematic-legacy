package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.Variable;
import ch.njol.skript.util.AsyncEffect;
import ch.njol.util.Kleenean;
import com.efnilite.skematic.objects.Schematic;
import com.efnilite.skematic.objects.SchematicLoader;
import org.bukkit.event.Event;

import java.io.File;

@Name("Load schematic")
@Description("Load a schematic file (and store it in a variable)")
@Examples("load skematic \"plugins/WorldEdit/schematics/lobby-3.schematic\" as \"loaded-schematic\" stored in {skematic::lobby-3}")
@Since("2.1")
public class EffSchematicLoad extends AsyncEffect {

    private Expression<String> schematic;
    private Expression<String> alias;
    private Variable<?> var;

    static {
        Skript.registerEffect(EffSchematicLoad.class, "load s(k|ch)em[atic] %strings% [as %-strings%] [[and] (store[d]|save) ([[the] s(k|ch)em[atic]]|it) (in|to) [variable] %-objects%]");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = (Expression<String>) expressions[0];
        alias = (Expression<String>) expressions[1];
        if (expressions[2] instanceof Variable<?>) {
            var = (Variable<?>) expressions[2];
        } else {
            Skript.error("You can only store loaded schematics in variables!");
        }

        return true;
    }

    @Override
    protected void execute(Event e) {
        String schematic = this.schematic.getSingle(e);
        String alias = this.alias != null ? this.alias.getSingle(e) : schematic;

        if (schematic == null) {
            return;
        }

        Schematic schem = new Schematic(new File(schematic));
        Object[] change = new Object[] { schem };
        if (var != null) {
            var.change(e, change, Changer.ChangeMode.SET);
        }
        SchematicLoader.add(alias, schem);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "load " + schematic.toString(e, debug);
    }
}