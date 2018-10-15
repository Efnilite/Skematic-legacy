package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;

@Name("Selection")
@Description("Gets the selection of a player (region)")
@Examples("set {_size} to selection of player")
@Since("1.0.0")
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
