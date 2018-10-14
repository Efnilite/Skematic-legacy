package me.efnilite.skematic.elements;

import ch.njol.skript.SkriptConfig;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.entity.Player;
import org.eclipse.jdt.annotation.Nullable;

public class Types {

    static {

        Classes.registerClass(new ClassInfo<>(FawePlayer.class, "faweplayer")
                .user("fawe ?players?")
                .parser(new Parser<FawePlayer>() {

                    @Override
                    @Nullable
                    public FawePlayer parse(String s, ParseContext context) {
                        return FaweAPI.wrapPlayer(s);
                    }

                    @Override
                    public String toString(FawePlayer o, int flags) {
                        return o.getName();
                    }

                    @SuppressWarnings("null")
                    @Override
                    public String toVariableNameString(FawePlayer p) {
                        if (SkriptConfig.usePlayerUUIDsInVariableNames.value()) {
                            return "" + p.getUUID();
                        } else {
                            return "" + p.getName();
                        }
                    }

                    @Override
                    public String getVariableNamePattern() {
                        if (SkriptConfig.usePlayerUUIDsInVariableNames.value()) {
                            return "[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}";
                        } else {
                            return "\\S+";
                        }
                    }
                }));

        Converters.registerConverter(Player.class, FawePlayer.class, (Converter<Player, FawePlayer>) p ->
                FaweAPI.wrapPlayer(p)
        );

        /*Classes.registerClass(new ClassInfo<>(World.class, "weworld")
                .user("we ?worlds?")
                .parser(new Parser<World>() {

                    @Override
                    @Nullable
                    public World parse(String s, ParseContext context) {
                        return BukkitUtil.getLocalWorld(Bukkit.getServer().getWorld(s));
                    }

                    @Override
                    public String toString(World w, int flags) {
                        return w.getName();
                    }

                    @SuppressWarnings("null")
                    @Override
                    public String toVariableNameString(World w) {
                        return w.getName();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                }));

        Converters.registerConverter(org.bukkit.World.class, World.class, (Converter<org.bukkit.World, World>) w ->
                BukkitUtil.getLocalWorld(w)
        );

        Converters.registerConverter(World.class, org.bukkit.World.class, (Converter<World, org.bukkit.World>) w ->
                Bukkit.getServer().getWorld(w.toString())
        );*/


    }

}
