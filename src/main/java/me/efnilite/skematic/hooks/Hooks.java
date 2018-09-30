package me.efnilite.skematic.hooks;

import me.efnilite.skematic.hooks.worldguard.WorldGuard;
import org.bukkit.Bukkit;

public class Hooks {

    static {

        if (getHook("WorldGuard")) WorldGuard.register();

    }

    public static boolean getHook(String p) {
        return Bukkit.getServer().getPluginManager().getPlugin(p) != null;
    }

}