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
@Return(Region.class)
@PropertyExpression
public class ExprSelection extends SkematicPropertyExpression<Player, Region> {

    static {
        register(ExprSelection.class, Region.class, "[(skematic|fawe)] selection[s]", "players");
    }

    @Override
    public Region convert(Player p) {
        LocalSession session = FaweAPI.wrapPlayer(p).getSession();
        try {
            Region selection = session.getSelection(session.getSelectionWorld());
            return new CuboidRegion(selection.getWorld(), selection.getMaximumPoint(), selection.getMaximumPoint());
        } catch (IncompleteRegionException e) {
            return null;
        }
    }

    @Override
    protected String getPropertyName() {
        return "selection";
    }
}
