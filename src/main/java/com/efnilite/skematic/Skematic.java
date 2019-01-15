package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.efnilite.skematic.lang.queue.SchematicQueue;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class Skematic extends JavaPlugin {

    private static Skematic instance;
    private static SkriptAddon addon;
    private static SchematicQueue schematicQueue;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this).setLanguageFileDirectory("lang");
        schematicQueue = new SchematicQueue();

        try {
            addon.loadClasses("com.efnilite.skematic", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void log(String s, Level level) {
        instance.getLogger().log(level, s);
    }

    public static Skematic getInstance() {
        return instance;
    }

    public static SchematicQueue getSchematicQueue() {
        return schematicQueue;
    }
}