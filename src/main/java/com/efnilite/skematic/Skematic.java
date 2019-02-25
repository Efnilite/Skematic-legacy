package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Skematic extends JavaPlugin {

    private static Skematic instance;
    private static SkriptAddon addon;

    @Override
    public void onEnable() {

        if (getServer().getPluginManager().getPlugin("FastAsyncWorldEdit") == null) {

            getLogger().severe("You need FastAsyncWorldEdit for Skematic to work! Disabling..");

            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        instance = this;
        addon = Skript.registerAddon(this).setLanguageFileDirectory("lang");
        try {
            addon.loadClasses("com.efnilite.skematic", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Skematic getInstance() {
        return instance;
    }

    public static SkriptAddon getAddon() {
        return addon;
    }
}