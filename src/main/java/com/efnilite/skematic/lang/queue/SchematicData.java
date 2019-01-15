package com.efnilite.skematic.lang.queue;

import com.efnilite.skematic.lang.PasteOptions;
import org.bukkit.Location;

import java.io.File;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

public class SchematicData {

    private Set<PasteOptions> options;
    private Location location;
    private File schematic;

    public SchematicData(File schematic, PasteOptions... options) {
        this.schematic = schematic;
        this.options = EnumSet.of(options[0], options);
    }

    public SchematicData(File schematic) {
       this.schematic = schematic;
       this.options = EnumSet.noneOf(PasteOptions.class);
    }

    public void setOptions(PasteOptions... options) {
        this.options.addAll(Arrays.asList(options));
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public File getSchematic() {
        return schematic;
    }

    public Set<PasteOptions> getOptions() {
        return options;
    }

    public Location getLocation() {
        return location;
    }


}