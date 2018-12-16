package com.efnilite.skematic.elements.expressions;

import com.boydti.fawe.object.FawePlayer;
import com.efnilite.skematic.lang.SkematicPropertyExpression;
import com.efnilite.skematic.lang.annotations.Patterns;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.command.tool.InvalidToolBindException;
import org.bukkit.entity.Player;

@Patterns({"brush size[s]", "players"})
@Return(Number.class)
@PropertyExpression
public class ExprBrushSize extends SkematicPropertyExpression<Player, Number> {

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
}
