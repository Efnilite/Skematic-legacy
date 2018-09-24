package me.efnilite.skematic.util;

import org.bukkit.Bukkit;

public class Utilities {

    public static void sendConsoleMessage(String msg) {
        System.out.println("[Skematic] " + msg);
    }

    public static void log(String m) {
        Bukkit.getServer().getConsoleSender().sendMessage("[TowerDefence] " + m);
    }

    public static void error(String m, Exception e, boolean log) {
        Bukkit.getServer().getLogger().severe("[Skematic] " + m);
        if (log) {
            e.printStackTrace();
        }
    }

    public static void warn(String m) {
        Bukkit.getServer().getLogger().warning("[Skematic] " + m);
    }


}
