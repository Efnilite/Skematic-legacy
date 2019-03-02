package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.efnilite.skematic.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Skematic extends JavaPlugin {

    private static Skematic instance;
    private static SkriptAddon addon;
    private static Metrics metrics;

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

        getLogger().info("Enabling Metrics..");
        metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("skript_version", () -> Bukkit.getPluginManager().getPlugin("Skript").getDescription().getVersion()));
        metrics.addCustomChart(new Metrics.SimplePie("fawe_version", () -> Bukkit.getPluginManager().getPlugin("FastAsyncWorldEdit").getDescription().getVersion()));
    }

    public static Skematic getInstance() {
        return instance;
    }

    public static SkriptAddon getAddon() {
        return addon;
    }
}