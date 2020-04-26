package com.kul.window.async;

import io.reactivex.Scheduler;

import java.util.concurrent.ThreadPoolExecutor;

public interface ExecutorsFactory {
    ThreadPoolExecutor noQueueNamedSingleThreadExecutor(String namePattern);
    Scheduler platformScheduler();
}
