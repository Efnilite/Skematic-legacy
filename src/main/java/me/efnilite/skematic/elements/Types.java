package me.efnilite.skematic.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;

import java.io.File;

public class Types {

    static {

        Classes.registerClass(new ClassInfo<>(File.class, "schematic")
                .user("?schematics?")
                .parser(new Parser<File>() {

                    @Override
                    public File parse(String s, ParseContext context) {
                        return new File(s);
                    }

                    @Override
                    public String toString(File o, int flags) {
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(File o) {
                        return o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                }));

        Converters.registerConverter(String.class, File.class, (Converter<String, File>) s ->
                new File(s)
        );

    }

}
