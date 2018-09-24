package me.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Skematic extends JavaPlugin {

    private String version = "1.0-SNAPSHOT";
    private static Skematic instance;
    public SkriptAddon addon;

    @Override
    public void onEnable() {

        instance = this;
        addon = Skript.registerAddon(this);

        try {
            addon.loadClasses("me.efnilite.skematic", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();

        }
        Utilities.sendConsoleMessage("Enabled Skematic " + version);

    }

    @Override
    public void onDisable() {

        Utilities.sendConsoleMessage("Disabled Skematic " + version);

    }

    public static Skematic getInstance() {
        return instance;
    }

    public SkriptAddon getAddonInstance() {
        return addon;
    }

}
