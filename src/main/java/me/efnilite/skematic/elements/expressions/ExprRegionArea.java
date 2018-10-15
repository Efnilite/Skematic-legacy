package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;

@Name("Selection area")
@Description("Get the region area of a player's selection.")
@Examples("set {area} to selection area of player")
@Since("1.0.0")
public class ExprRegionArea extends SimplePropertyExpression<Player, Number> {

    static {
        register(ExprRegionArea.class, Number.class, "selection area[s]", "players");
    }

    @Override
    public Number convert(final Player p) {
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