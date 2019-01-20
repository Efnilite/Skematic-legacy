package com.efnilite.skematic.objects;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.clipboard.ReadOnlyClipboard;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Schematic {

    private File file;
    private Clipboard clipboard;

    public Schematic(File file) {
        this.file = file;
        try {
            this.clipboard = FaweAPI.load(file).getClipboard();
        } catch (IOException e) {
            this.clipboard = null;
        }
    }

    public Schematic(CuboidRegion cuboid) {
        this.file = null;
        this.clipboard = new BlockArrayClipboard(cuboid, ReadOnlyClipboard.of(cuboid.getWorld(), cuboid));
    }

    public void paste(World world, Vector vector, Set<PasteOptions> options) {
        FaweUtils.toSchematic(file).paste(world,
                vector,
                false,
                !options.contains(PasteOptions.AIR),
                !options.contains(PasteOptions.ENTITIES),
                null);
    }

    public void save(File file, ClipboardFormat format) {
        new Schematic(file).save(file, format);
    }

    public String getName() {
        return file.toString();
    }

    public File getFile() {
        return file;
    }

    public Clipboard getClipboard() {
        return clipboard;
    }
}
