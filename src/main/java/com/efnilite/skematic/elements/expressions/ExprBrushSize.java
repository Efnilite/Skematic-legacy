package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.command.tool.InvalidToolBindException;
import org.bukkit.entity.Player;

public class ExprBrushSize extends SimplePropertyExpression<Player, Number> {

    static {
        register(ExprBrushSize.class, Number.class, "brush size", "players");
    }

    @Override
    public Number convert(Player player) {
        FawePlayer fawePlayer = FawePlayer.wrap(player);
        try {
            return fawePlayer.getSession().getBrushTool(fawePlayer.toWorldEditPlayer()).getSize();
        } catch (InvalidToolBindException e) {
            return null;
        }
    }

    @Override
    protected String getPropertyName() {
        return "brush type";
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }
}
