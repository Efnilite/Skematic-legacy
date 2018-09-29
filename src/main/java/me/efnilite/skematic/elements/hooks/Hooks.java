package me.efnilite.skematic.elements.hooks;

import me.efnilite.skematic.elements.hooks.worldguard.WorldGuard;
import org.bukkit.Bukkit;

public class Hooks {

    static {

        if (getHook("WorldGuard")) WorldGuard.register();

    }

    public static boolean getHook(String p) {
        return Bukkit.getServer().getPluginManager().getPlugin(p) != null;
    }

}
