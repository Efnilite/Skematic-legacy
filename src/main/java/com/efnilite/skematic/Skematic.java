package com.efnilite.skematic;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.world.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class Skematic extends JavaPlugin {

    private static Skematic instance;
    private static SkriptAddon addon;
    private static Register register;

    @Override
    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this).setLanguageFileDirectory("lang");
        register = new Register();

        try {
            addon.loadClasses("com.efnilite.skematic", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Skematic.log("Enabled Skematic " + getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        Skematic.log("Disabled Skematic " + getDescription().getVersion());
    }

    public static void log(String s, Level level) {
        instance.getLogger().log(level, s);
    }

    private static void log(String s) {
        instance.getLogger().info(s);
    }

    public static Skematic getInstance() {
        return instance;
    }

    public static Register getRegister() {
        return register;
    }
}