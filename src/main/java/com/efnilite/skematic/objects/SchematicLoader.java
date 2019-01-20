package com.efnilite.skematic.objects;

import java.util.HashMap;

public class SchematicLoader {

    private static HashMap<String, Schematic> schematics = new HashMap<>();
    private static Schematic lastLoaded;

    public static void add(String alias, Schematic schematic) {
        schematics.put(alias, schematic);
        lastLoaded = schematic;
    }

    public static Schematic get(String alias) {
        return schematics.get(alias);
    }

    public static void remove(String alias) {
        schematics.remove(alias);
    }

    public static HashMap<String, Schematic> getSchematics() {
        return schematics;
    }

    public static Schematic getLastLoaded() {
        return lastLoaded;
    }
}
