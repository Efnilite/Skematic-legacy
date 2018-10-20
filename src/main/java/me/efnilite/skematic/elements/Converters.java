package me.efnilite.skematic.elements;

import ch.njol.skript.classes.Converter;

import java.io.File;

public class Converters {

    static {

        ch.njol.skript.registrations.Converters.registerConverter(String.class, File.class, (Converter<String, File>) s ->
                new File(s)
        );

    }

}
