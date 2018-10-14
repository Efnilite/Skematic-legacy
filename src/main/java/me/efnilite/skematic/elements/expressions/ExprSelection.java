package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;

public class ExprSelection extends SimplePropertyExpression<Player, Region> {

    static {
        register(ExprSelection.class, Region.class, "selection[s]", "players");
    }

    @Override
    public Region convert(final Player p) {
        LocalSession session = FaweAPI.wrapPlayer(p).getSession();
        if (session.getSelection(session.getSelectionWorld()) != null) {
            return session.getSelection(session.getSelectionWorld());
        } else {
            return null;
        }
    }

    @Override
    protected String getPropertyName() {
        return "selection";
    }

    @Override
    public Class<Region> getReturnType() {
        return Region.class;
    }

}
