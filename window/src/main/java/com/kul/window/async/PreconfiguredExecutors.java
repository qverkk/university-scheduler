package com.kul.window.async;

import io.reactivex.Scheduler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PreconfiguredExecutors implements ExecutorsFactory {

    private final Scheduler scheduler;

    public PreconfiguredExecutors(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public ThreadPoolExecutor noQueueNamedSingleThreadExecutor(String namePattern) {
        return new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                new NamedThreadFactory(namePattern + "-%d"),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Override
    public Scheduler platformScheduler() {
        return scheduler;
    }
}
