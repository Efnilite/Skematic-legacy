package me.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.efnilite.skematic.elements.Registery;
import me.efnilite.skematic.hooks.Hooks;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Skematic extends JavaPlugin {

    private static Skematic instance;
    public static SkriptAddon addon;

    @Override
    public void onEnable() {

        instance = this;
        addon = Skript.registerAddon(this);

        Hooks.load();

        Registery.load();

        try {
            addon.loadClasses("me.efnilite.skematic", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Utilities.sendConsoleMessage("Enabled Skematic " + getDescription().getVersion());

    }

    @Override
    public void onDisable() {

        Utilities.sendConsoleMessage("Disabled Skematic " + getDescription().getVersion());

    }

    public static Skematic getInstance() {
        return instance;
    }

    public static SkriptAddon getAddonInstance() {
        return addon;
    }

}