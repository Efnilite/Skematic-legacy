package me.efnilite.skematic.elements.hooks.worldguard;

import ch.njol.skript.Skript;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.efnilite.skematic.elements.hooks.worldguard.effects.EffCreateRegion;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldGuard {

    static {
        if (getWorldGuard() != null) {

            Utilities.log("Found WorldGuard! Registering syntaxes now..");

            // Effects

            Skript.registerEffect(EffCreateRegion.class, "create [a] [new] [worldguard] region [(named|called)] %string% with [editsession] of %player%");


        }
    }

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

}
