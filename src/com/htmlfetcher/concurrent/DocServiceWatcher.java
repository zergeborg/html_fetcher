package com.htmlfetcher.concurrent;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: Gergely Nagy
 * Date: 2013.03.27.
 * Time: 21:31
 */
public class DocServiceWatcher implements Runnable {

    private final ThreadPoolExecutor executor;
    private boolean finished;

    public DocServiceWatcher(ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        while (!finished) {
            System.out.println(
                String.format("Queue: %d/%d(%d), Active: %d, Completed: %d, Queue: %d/%d, Task: %d",
                    executor.getPoolSize(), executor.getCorePoolSize(), executor.getMaximumPoolSize(),
                    executor.getActiveCount(),
                    executor.getCompletedTaskCount(),
                    executor.getQueue().size(), executor.getQueue().remainingCapacity() + executor.getQueue().size(),
                    executor.getTaskCount()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }

    public synchronized void setFinished(boolean finished) {
        this.finished = finished;
    }
}
