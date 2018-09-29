package me.efnilite.skematic.elements.hooks.worldguard;

import ch.njol.skript.Skript;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.efnilite.skematic.elements.hooks.worldguard.effects.EffAddOwner;
import me.efnilite.skematic.elements.hooks.worldguard.effects.EffCreateRegion;
import me.efnilite.skematic.elements.hooks.worldguard.effects.EffDelRegion;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldGuard {

    public static WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public static void register() {

        Utilities.log("Found WorldGuard! Registering syntaxes now..");

        // Effects

        Skript.registerEffect(EffCreateRegion.class, "create [the] [new] [worldguard] region [(named|called)] %string% with [the] [sel[ection]] of %player% [(with|and) world %world%]");

        Skript.registerEffect(EffDelRegion.class, "del[ete] [the] [worldguard] region [(named|call)] %string% in [world] %world%");

        Skript.registerEffect(EffAddOwner.class, "add %player% to [the] [worldguard] region %string% in [world] %world%");

    }

}
