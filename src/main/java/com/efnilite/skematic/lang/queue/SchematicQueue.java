package com.efnilite.skematic.lang.queue;

import com.efnilite.skematic.lang.PasteOptions;
import com.efnilite.skematic.utils.FaweUtils;
import org.bukkit.Location;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class SchematicQueue {

    private static Queue<SchematicData> queue = new LinkedList<>();
    private static SchematicData lastQueued;

    public static void addToQueue(SchematicData schematic) {
        if (queue.contains(schematic)) {
            throw new IllegalArgumentException("That schematic is already in the list!");
        } else {
            queue.add(schematic);
            lastQueued = schematic;
        }
    }

    public static void runNext() {
        SchematicData data = queue.element();
        queue.remove();

        FaweUtils.toSchematic(data.getSchematic()).paste(FaweUtils.getWorld(data.getLocation().getWorld().getName()),
                FaweUtils.toVector(data.getLocation()),
                false,
                !data.getOptions().contains(PasteOptions.AIR),
                !data.getOptions().contains(PasteOptions.ENTITIES),
                null
        );
    }

    public static void complete() {
        for (SchematicData data : queue) {
            FaweUtils.toSchematic(data.getSchematic()).paste(FaweUtils.getWorld(data.getLocation().getWorld().getName()),
                    FaweUtils.toVector(data.getLocation()),
                    false,
                    !data.getOptions().contains(PasteOptions.AIR),
                    !data.getOptions().contains(PasteOptions.ENTITIES),
                    null
            );
        }
        queue.clear();
    }


    public static void change(File schematic, Location location) {
        queue.stream()
                .filter(data -> data.getSchematic().equals(schematic))
                .collect(Collectors.toList())
                .get(0)
                .setLocation(location);
    }

    public static void change(File schematic, PasteOptions... options) {
        queue.stream()
                .filter(data -> data.getSchematic().equals(schematic))
                .collect(Collectors.toList())
                .get(0)
                .setOptions(options);
    }

    public static List<SchematicData> getQueue() {
        List<SchematicData> list = new ArrayList<>();
        while (queue.iterator().hasNext()) {
            list.add(queue.iterator().next());
        }
        return list;
    }

    public static SchematicData getLastQueued() {
        return lastQueued;
    }
}
