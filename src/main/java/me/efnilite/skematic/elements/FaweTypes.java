package me.efnilite.skematic.elements;

import ch.njol.skript.SkriptConfig;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.FawePlayer;
import com.sk89q.worldedit.bukkit.selections.Selection;
import org.eclipse.jdt.annotation.Nullable;

public class FaweTypes {

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

    }

}
