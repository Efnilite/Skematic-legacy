package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import org.bukkit.entity.Player;

@Name("Selection area")
@Description("Get the region area of a player's selection.")
@Examples("set {area} to selection area of player")
@Since("1.0.0")
public class ExprCuboidRegionArea extends SimplePropertyExpression<Player, Number> {

    static {
        register(ExprCuboidRegionArea.class, Number.class, "selection area[s]", "players");
    }

    @Override
    public Number convert(final Player p) {

        if (p == null) {
            return null;
        }

        return FaweAPI.wrapPlayer(p).getSelection().getArea();
    }

    @Override
    protected String getPropertyName() {
        return "selection area";
    }

    @Override
    public Class<Number> getReturnType() {
        return Number.class;
    }
}