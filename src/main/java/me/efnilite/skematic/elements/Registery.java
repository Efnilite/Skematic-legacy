package me.efnilite.skematic.elements;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import com.sk89q.worldedit.Vector;
import me.efnilite.skematic.elements.effects.*;
import me.efnilite.skematic.elements.expressions.*;

public class Registery {

    // Expressions

    static {
        Skript.registerExpression(ExprSchematicArea.class, Number.class, ExpressionType.COMBINED, "[skematic] (1¦width|2¦height|3¦lenght) of [the] [schem[atic]] %string%");

        Skript.registerExpression(ExprSchematicAreaCoord.class, Number.class, ExpressionType.COMBINED, "[skematic] [the] (1¦x|2¦y|3¦z)(-| )coord[inate] of [the] [schem[atic]] %string%");

        Skript.registerExpression(ExprSchematicDimensions.class, Vector.class, ExpressionType.COMBINED, "[skematic] [the] dimension[s] of [the] [schem[atic]] %string%");

        Skript.registerExpression(ExprSchematicOrigin.class, Vector.class, ExpressionType.COMBINED, "[skematic] [the] origin [area] of [schem[atic]] %string%");
    }

    // Effects

    static {
        Skript.registerEffect(EffDownloadFile.class, "download [the] [file] from %string% to [the] [file] %string%");

        Skript.registerEffect(EffPasteSchematic.class, "paste [a] [new] schem[atic] %string% at [loc[ation]] %location% [(without|excluding) air %-boolean%[(,| and) allow[ing] undo %-boolean%]]");

        Skript.registerEffect(EffNewAsyncWorld.class, "(create|load) [a] [new] [async[hronous]] [world] %object% [sav[(e[d]|ing)]] (as|to) %object%");

        Skript.registerEffect(EffDelAsyncWorld.class, "del[ete] [the] [async[hronous]] [world] %object%");

        Skript.registerEffect(EffSaveAsyncWorld.class, "save [the] [async[hronous]] [world] %object%");
    }

}