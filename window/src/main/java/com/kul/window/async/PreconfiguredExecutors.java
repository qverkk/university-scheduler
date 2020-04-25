package com.kul.window.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PreconfiguredExecutors {
    public static ThreadPoolExecutor noQueueNamedSingleThreadExecutor(String namePattern) {
        return new ThreadPoolExecutor(
                1, 1, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),
                new NamedThreadFactory(namePattern),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
