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

    public void laterAsync(Runnable r, long d) {
        if (r != null) {
            get().getServer().getScheduler().runTaskLaterAsynchronously(get(), r, d).getTaskId();
        }
    }

    public void laterSync(Runnable r, long d) {
        if (r != null) {
            get().getServer().getScheduler().runTaskLater(get(), r, d).getTaskId();
        }
    }
}
