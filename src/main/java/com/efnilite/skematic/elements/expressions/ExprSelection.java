package com.efnilite.skematic.elements.expressions;

import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Name;
import com.boydti.fawe.FaweAPI;
import com.efnilite.skematic.lang.SkematicPropertyExpression;
import com.efnilite.skematic.lang.annotations.PropertyExpression;
import com.efnilite.skematic.lang.annotations.Return;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;

@Name("Selection")
@Description("Gets the selection of a player (region)")
@Return(CuboidRegion.class)
@PropertyExpression
public class ExprSelection extends SkematicPropertyExpression<Player, CuboidRegion> {

    static {
        register(ExprSelection.class, CuboidRegion.class, "[(skematic|fawe)] selection[s]", "players");
    }

    @Override
    public CuboidRegion convert(final Player p) {
        LocalSession session = FaweAPI.wrapPlayer(p).getSession();
        try {
            Region selection = session.getSelection(session.getSelectionWorld());
            return new CuboidRegion(selection.getWorld(), selection.getMaximumPoint(), selection.getMinimumPoint());
        } catch (IncompleteRegionException e) {
            return null;
        }
    }

    @Override
    protected String getPropertyName() {
        return "selection";
    }
}
