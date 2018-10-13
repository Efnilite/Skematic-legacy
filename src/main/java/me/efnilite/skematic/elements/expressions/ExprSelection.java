package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.entity.Player;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.event.Event;

public class ExprSelection extends SimpleExpression<Region> {

    static {
        Skript.registerExpression(ExprSelection.class, Region.class, ExpressionType.PROPERTY, "selection[s]", "players");
    }

    private Expression<Player> player;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {

        player = (Expression<Player>) exprs[0];

        return true;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Region> getReturnType() {
        return Region.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "selection of player " + player.toString(e, debug);
    }

    @Override
    protected Region[] get(Event e) {
        Player p = player.getSingle(e);
        LocalSession session = FaweAPI.wrapPlayer(p).getSession();
        return CollectionUtils.array(session.getSelection(session.getSelectionWorld()));
    }
}
