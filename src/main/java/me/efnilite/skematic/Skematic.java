package me.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.ExpressionType;
import me.efnilite.skematic.syntaxes.ExprSchematicArea;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Skematic extends JavaPlugin {

    private String version = "1.0.0";
    private Skematic instance;
    private SkriptAddon addon;

    @Override
    public void onEnable() {

        instance = this;
        addon = Skript.registerAddon(this);

        try {
            addon.loadClasses("me.efnilite.skematic", "syntaxes");
            Skript.registerExpression(ExprSchematicArea.class, Integer.class, ExpressionType.SIMPLE, "[the] (1¦y(-| )coord[inate]|height|2¦x(-| )coord[inate]|width|3¦z(-| )coord[inate]|length|4¦dimension[s]|[schem[atic]] area) of [schem[atic]] %string%");
        } catch (IOException exception) {
            exception.printStackTrace();

        }
        sendConsoleMessage("Enabled Skematic " + version);

    }

    @Override
    public void onDisable() {

        sendConsoleMessage("Disabled Skematic " + version);

    }

    public Skematic getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

    public void sendConsoleMessage(String msg) {
        System.out.println("[Skematic] " + msg);
    }

}
