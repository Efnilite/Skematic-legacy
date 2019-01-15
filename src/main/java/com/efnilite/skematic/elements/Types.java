package com.efnilite.skematic.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Converter;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import ch.njol.skript.util.EnumUtils;
import com.efnilite.skematic.lang.PasteOptions;
import com.efnilite.skematic.lang.queue.SchematicData;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.eclipse.jdt.annotation.Nullable;

import java.io.File;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(CuboidRegion.class, "cuboidregion")
                .user("cuboidregions?")
                .parser(new Parser<CuboidRegion>() {

                    @Override
                    public CuboidRegion parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(CuboidRegion o, int flags) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(CuboidRegion o) {
                        return o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                }));

        /*Classes.registerClass(new ClassInfo<>(File.class, "schematic")
                .user("skematics?")
                .parser(new Parser<File>() {

                    @Override
                    public File parse(String s, ParseContext context) {
                        return new File(s);
                    }

                    @Override
                    public String toString(File o, int flags) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(File o) {
                        return o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                }));*/

        Classes.registerClass(new ClassInfo<>(SchematicData.class, "schematicdata")
                .user("s(k|ch)ematicdatas?")
                .parser(new Parser<SchematicData>() {

                    @Override
                    public SchematicData parse(String s, ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(SchematicData o, int flags) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(SchematicData o) {
                        return o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                }));


        EnumUtils<PasteOptions> pasteOptions = new EnumUtils<>(PasteOptions.class, "pasteoption");
        Classes.registerClass(new ClassInfo<>(PasteOptions.class, "pasteoption")
                .user("pasteoptions?")
                .parser(new Parser<PasteOptions>() {

                    @Override
                    public PasteOptions parse(String s, ParseContext context) {
                        return pasteOptions.parse(s);
                    }

                    @Override
                    public String toString(PasteOptions o, int flags) {
                        return pasteOptions.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(PasteOptions o) {
                        return o.name();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }
                }));

        EnumUtils<ClipboardFormat> clipboardFormats = new EnumUtils<>(ClipboardFormat.class, "schematicformat");
        Classes.registerClass(new ClassInfo<>(ClipboardFormat.class, "schematicformat")
                .user("schematicformats?")
                .defaultExpression(new EventValueExpression<>(ClipboardFormat.class))
                .parser(new Parser<ClipboardFormat>() {

                    @Override
                    @Nullable
                    public ClipboardFormat parse(String s, ParseContext context) {
                        return clipboardFormats.parse(s);
                    }

                    @Override
                    public String toString(ClipboardFormat o, int flags) {
                        return clipboardFormats.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(ClipboardFormat o) {
                        return o.name();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "\\S+";
                    }

                }));

        Converters.registerConverter(String.class, File.class, (Converter<String, File>) s ->
                new File(s)
        );

        Converters.registerConverter(File.class, String.class, (Converter<File, String>) s ->
                s.toString()
        );
    }
}
