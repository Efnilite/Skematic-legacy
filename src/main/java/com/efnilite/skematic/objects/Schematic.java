package com.efnilite.skematic.objects;

import com.boydti.fawe.object.clipboard.ReadOnlyClipboard;
import com.efnilite.skematic.utils.FaweUtils;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class Schematic {

    private File file;
    private Clipboard clipboard;

    public Schematic(File file) {
        this.file = file;
        this.clipboard = FaweUtils.toSchematic(file).getClipboard();
    }

    public Schematic(CuboidRegion cuboid) {
        this.file = null;
        this.clipboard = new BlockArrayClipboard(cuboid, ReadOnlyClipboard.of(FaweUtils.getEditSession(Bukkit.getWorld(cuboid.getWorld().getName())), cuboid));
    }

    public void paste(World world, Vector vector, Set<PasteOptions> options) {
        FaweUtils.toSchematic(clipboard).paste(world, vector, !options.contains(PasteOptions.AIR));
    }

    public void save(File file, ClipboardFormat format) {
        try {
            FaweUtils.toSchematic(file).save(file, format);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
