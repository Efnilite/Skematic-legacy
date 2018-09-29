package me.efnilite.skematic.hooks;

import me.efnilite.skematic.Skematic;
import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import org.bukkit.Bukkit;

import java.io.IOException;

public class Hooks {

    public static void load() {

        if (getHook("WorldGuard")) {
            Hooks.registerHook("worldguard");
            WorldGuard.register();
        }
    }

    public static boolean getHook(String p) {
        return Bukkit.getServer().getPluginManager().getPlugin(p) != null;
    }

    public static void registerHook(String p) {
        try {
            Skematic.getAddonInstance().loadClasses("me.efnilite.skematic", "hooks." + p);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
