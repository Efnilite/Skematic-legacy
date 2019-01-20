package com.efnilite.skematic.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SchematicCache {

    private static List<Schematic> schematics = new ArrayList<>();
    private static Schematic lastLoaded;

    public static void add(Schematic schematic) {
        schematics.add(schematic);
        lastLoaded = schematic;
    }

    public static void remove(File schematic) {
        schematics.remove(schematic);
    }

    public static List<Schematic> getSchematics() {
        return schematics;
    }

    public static Schematic getLastLoaded() {
        return lastLoaded;
    }
}
