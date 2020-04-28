package com.kul.window.async;

import java.util.IllegalFormatException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger counter = new AtomicInteger();
    private final ThreadFactory threadFactory = Executors.defaultThreadFactory();

    private final String nameFormat;

    public NamedThreadFactory(String namePattern) {
        this.nameFormat = namePattern + "-%d-thread";

        try {
            String.format(namePattern, 0);
        } catch (IllegalFormatException ex) {
            throw new RuntimeException("Invalid thread name pattern - required single numeric parameter", ex);
        }
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = threadFactory.newThread(r);
        thread.setName(String.format(nameFormat, counter.getAndIncrement()));
        return thread;
    }
}
