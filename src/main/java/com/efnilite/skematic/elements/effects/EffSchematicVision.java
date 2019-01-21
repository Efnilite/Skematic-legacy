package com.efnilite.skematic.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.boydti.fawe.object.schematic.visualizer.SchemVis;
import com.efnilite.skematic.objects.Schematic;
import com.efnilite.skematic.objects.SchematicLoader;
import com.efnilite.skematic.utils.FaweUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class EffSchematicVision extends Effect {

    private Expression<?> schematic;
    private Expression<Player> player;

    static {
        Skript.registerEffect(EffSchematicVision.class, "make %players% see %schematics/strings%");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {

        schematic = expressions[0];
        player = (Expression<Player>) expressions[1];

        return true;
    }

    @Override
    protected void execute(Event e) {
        Player player = this.player.getSingle(e);

        if (this.schematic == null || player == null) {
            return;
        }

        Schematic schematic;
        SchemVis vision = new SchemVis(FaweUtils.getPlayer(player));
        if (this.schematic.getSingle(e) instanceof String) {
            String file = (String) this.schematic.getSingle(e);
            if (SchematicLoader.getSchematics().containsKey(file)) {
                schematic = SchematicLoader.get(file);
            } else if (Paths.get(file).toFile().exists()) {
                schematic = new Schematic(new File(file));
            } else {
                Skript.error("Schematic " + file + " doesn't exist!");
                return;
            }
        } else if (this.schematic.getSingle(e) instanceof Schematic) {
            schematic = (Schematic) this.schematic.getSingle(e);
        } else {
            return;
        }

        try {
            vision.add(schematic.getFile());
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        vision.update();
        vision.bind();
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "make " + player.toString(e, debug) + " see " + schematic.toString(e, debug);
    }
}
