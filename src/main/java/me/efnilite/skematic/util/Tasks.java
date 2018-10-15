package me.efnilite.skematic.util;

public class Tasks extends TaskManager {

    public void async(Runnable r) {
        if (r != null) {
            get().getServer().getScheduler().runTaskAsynchronously(get(), r).getTaskId();
            r.run();
        }
    }

    public void sync(Runnable r) {
        if (r != null) {
            get().getServer().getScheduler().runTask(get(), r).getTaskId();
        }
    }

    public void laterAsync(Runnable r, long delay) {
        if (r != null) {
            get().getServer().getScheduler().runTaskLaterAsynchronously(get(), r, delay).getTaskId();
        }
    }

    public void laterSync(Runnable r, long delay) {
        if (r != null) {
            get().getServer().getScheduler().runTaskLater(get(), r, delay).getTaskId();
        }
    }

    public void repeatSync(Runnable r, long delay, long period) {
        if (r != null) {
            get().getServer().getScheduler().scheduleSyncRepeatingTask(get(), r, delay, period);
        }
    }

}
