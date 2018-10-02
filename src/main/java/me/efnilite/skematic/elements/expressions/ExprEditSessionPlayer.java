package me.efnilite.skematic.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.EditSession;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprEditSessionPlayer extends SimpleExpression<EditSession> {

    static {
        Skript.registerExpression(ExprEditSessionPlayer.class, EditSession.class, ExpressionType.PROPERTY, "[the] (edit(-| )session of %player%|%player%'s edit(-| )session)");
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
    public Class<? extends EditSession> getReturnType() {
        return EditSession.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "get the editsession of " + player.toString(e, debug);
    }

    @Override
    protected EditSession[] get(Event e) {
        return new EditSession[] { FaweAPI.wrapPlayer(player.getSingle(e)).getNewEditSession() };
    }
}
