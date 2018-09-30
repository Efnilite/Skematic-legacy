package me.efnilite.skematic.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.world.biome.BaseBiome;
import me.efnilite.skematic.elements.effects.*;
import me.efnilite.skematic.elements.expressions.*;

public class Registery {

    public static void load() {


        // Expressions


        Skript.registerExpression(ExprSchematicArea.class, Number.class, ExpressionType.COMBINED, "[skematic] (1¦width|2¦height|3¦lenght) of [the] [schem[atic]] %string%");

        Skript.registerExpression(ExprSchematicAreaCoord.class, Number.class, ExpressionType.COMBINED, "[skematic] [the] (1¦x|2¦y|3¦z)(-| )coord[inate] of [the] [schem[atic]] %string%");

        Skript.registerExpression(ExprSchematicDimensions.class, Vector.class, ExpressionType.COMBINED, "[skematic] [the] dimension[s] of [the] [schem[atic]] %string%");

        Skript.registerExpression(ExprSchematicOrigin.class, Vector.class, ExpressionType.COMBINED, "[skematic] [the] origin [area] of [schem[atic]] %string%");

        Skript.registerExpression(ExprBiome.class, BaseBiome.class, ExpressionType.PROPERTY, "[skematic] [the] biome (of|at) %integer% to %integer% in [world] %string%");


        // Effects


        Skript.registerEffect(EffPasteSchematic.class, "paste [a] [new] schem[atic] %string% at [loc[ation]] %location% [(without|excluding) air %-boolean%[(,| and) allow[ing] undo %-boolean%]]");

        Skript.registerEffect(EffNewAsyncWorld.class, "(create|load) [a] [new] [async[hronous]] [world] %object% [sav[(e[d]|ing)]] (as|to) %object%");

        Skript.registerEffect(EffDelAsyncWorld.class, "del[ete] [the] [async[hronous]] [world] %object%");

        Skript.registerEffect(EffSaveAsyncWorld.class, "save [the] [async[hronous]] [world] %object%");

        Skript.registerEffect(EffWeather.class, "set [the] weather [condition] to (1¦rain|2¦thunder[[_]storm]|3¦clear) in [world] %world% [for %integer% sec[onds]]");

    }

}