package me.efnilite.skematic.util;

import org.bukkit.Bukkit;

public class Utilities {

    public static void sendConsoleMessage(String msg) {
        Bukkit.getLogger().info("[Skematic]" + msg);
    }

    public static void log(String m) {
        Bukkit.getServer().getConsoleSender().sendMessage("[Skematic] " + m);
    }

    public static void error(String m, Exception e, boolean log) {
        Bukkit.getLogger().severe("[Skematic] " + m);
        if (log) {
            e.printStackTrace();
        }
    }

    public static void warn(String m) {
        Bukkit.getLogger().warning("[Skematic] " + m);
    }


}
