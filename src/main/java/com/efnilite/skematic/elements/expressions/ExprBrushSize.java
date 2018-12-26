package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.object.FawePlayer;
import com.efnilite.skematic.lang.SkematicPropertyExpression;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.command.tool.InvalidToolBindException;
import org.bukkit.entity.Player;

@Name("Brush size")
@Description("Get the brush size of players.")
@Return(Number.class)
@PropertyExpression
public class ExprBrushSize extends SkematicPropertyExpression<Player, Number> {

    static {
        register(ExprBrushSize.class, Number.class, "brush size[s]", "players");
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
        return "brush size";
    }
}
