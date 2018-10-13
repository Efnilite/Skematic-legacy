package me.efnilite.skematic.elements;

import ch.njol.skript.classes.Converter;
import ch.njol.skript.registrations.Converters;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.entity.Player;

public class FaweConverters {

    static {

        Converters.registerConverter(Player.class, FawePlayer.class, (Converter<Player, FawePlayer>) p ->
                FaweAPI.wrapPlayer(p)
        );

        Converters.registerConverter(FawePlayer.class, Player.class, (Converter<FawePlayer, Player>) p ->
                p.getPlayer()
        );

    }


}
