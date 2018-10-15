package me.efnilite.skematic.util;

import me.efnilite.skematic.Skematic;

public abstract class TaskManager {

    public static TaskManager manager;

    public abstract void async(Runnable r);

    public abstract void sync(Runnable r);

    public abstract void laterAsync(Runnable r, long delay);

    public abstract void laterSync(Runnable r, long delay);

    public abstract void repeatSync(Runnable r, long delay, long period);

    public static Skematic get() {
        return Skematic.getInstance();
}

}
