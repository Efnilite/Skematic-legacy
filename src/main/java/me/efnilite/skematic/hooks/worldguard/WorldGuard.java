package me.efnilite.skematic.hooks.worldguard;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.efnilite.skematic.hooks.worldguard.effects.EffCreateRegion;
import me.efnilite.skematic.hooks.worldguard.effects.EffDelRegion;
import me.efnilite.skematic.hooks.worldguard.expressions.ExprRegionOwners;
import me.efnilite.skematic.hooks.worldguard.expressions.ExprVolumeRegion;
import me.efnilite.skematic.util.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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

        // Expressions

        Skript.registerExpression(ExprRegionOwners.class, Player.class, ExpressionType.COMBINED, "region %string% in [world] %world%");

        Skript.registerExpression(ExprVolumeRegion.class, Number.class, ExpressionType.COMBINED, "[the] volume of [region] %string% in [world] %world%");

    }

}
