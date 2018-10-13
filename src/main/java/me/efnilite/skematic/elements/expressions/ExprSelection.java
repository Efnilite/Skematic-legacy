package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.Region;

public class ExprSelection extends SimplePropertyExpression<FawePlayer, Region> {

    static {
        register(ExprSelection.class, Region.class, "selection[s]", "faweplayers");
    }

    @Override
    public Region convert(final FawePlayer p) {
        LocalSession session = FaweAPI.wrapPlayer(p).getSession();
        return session.getSelection(session.getSelectionWorld());
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
