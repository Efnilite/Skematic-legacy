package me.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class Skematic extends JavaPlugin {

    private static Skematic instance;
    private static SkriptAddon addon;

    @Override
    public void onEnable() {

        instance = this;
        addon = Skript.registerAddon(this).setLanguageFileDirectory("lang");

        try {
            addon.loadClasses("me.efnilite.skematic", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Skematic.log("Enabled Skematic " + getDescription().getVersion());

    }

    @Override
    public void onDisable() {

        Skematic.log("Disabled Skematic " + getDescription().getVersion());

    }

    public static Skematic getInstance() {
        return instance;
    }

    public static SkriptAddon getAddonInstance() {
        return addon;
    }

    public static void log(String s, Level level) {
        instance.getLogger().log(level, s);
    }

    public static void log(String s) {
        instance.getLogger().info(s);
    }

}