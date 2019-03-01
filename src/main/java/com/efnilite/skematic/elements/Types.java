package com.efnilite.skematic.elements;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.util.EnumUtils;
import com.efnilite.skematic.objects.PasteOptions;
import com.efnilite.skematic.objects.Schematic;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;

public class Types {

    static {
        Classes.registerClass(new ClassInfo<>(CuboidRegion.class, "cuboidregion")
                .user("cuboidregions?")
                .name("CuboidRegion")
                .description("A virtual region selection.")
                .since("1.0")
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
                        return o.toString();
                    }

                    @Override
                    public String toVariableNameString(CuboidRegion o) {
                        return "cuboidregion:" + o.toString();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "cuboidregion:.+";
                    }
                }));

        Classes.registerClass(new ClassInfo<>(Schematic.class, "schematic")
                .user("schematics?")
                .name("Schematic")
                .description("A schematic file.")
                .since("2.1")
                .parser(new Parser<Schematic>() {

                    @Override
                    public Schematic parse(final String s, final ParseContext context) {
                        return null;
                    }

                    @Override
                    public boolean canParse(final ParseContext context) {
                        return false;
                    }

                    @Override
                    public String toString(final Schematic o, int flags) {
                        return o.getFile().getPath();
                    }

                    @Override
                    public String toVariableNameString(final Schematic o) {
                        return "schematic:" + o.getFile().getPath();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "schematic:.+";
                    }
                }));

        EnumUtils<PasteOptions> pasteOptions = new EnumUtils<>(PasteOptions.class, "pasteoption");
        Classes.registerClass(new ClassInfo<>(PasteOptions.class, "pasteoption")
                .defaultExpression(new EventValueExpression<>(PasteOptions.class))
                .user("pasteoptions?")
                .name("Paste Options")
                .description("Options to change how you paste a schematic/cuboidregion. Current options: air, entities and biomes")
                .since("2.1")
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
                        return "pasteoptions:" + o.name();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "pasteoptions:.+";
                    }
                }));

        EnumUtils<ClipboardFormat> clipboardFormats = new EnumUtils<>(ClipboardFormat.class, "schematicformat");
        Classes.registerClass(new ClassInfo<>(ClipboardFormat.class, "schematicformat")
                .defaultExpression(new EventValueExpression<>(ClipboardFormat.class))
                .user("schematicformats?")
                .name("Schematic Format")
                .description("The format schematics are saved in.")
                .since("2.0")
                .parser(new Parser<ClipboardFormat>() {

                    @Override
                    public ClipboardFormat parse(String s, ParseContext context) {
                        return clipboardFormats.parse(s);
                    }

                    @Override
                    public String toString(ClipboardFormat o, int flags) {
                        return clipboardFormats.toString(o, flags);
                    }

                    @Override
                    public String toVariableNameString(ClipboardFormat o) {
                        return "clipboardformat:" + o.name();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return "clipboardformat:.+";
                    }

                }));
    }
}
