package com.example.moviedpapp.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private static AppExecutors appExecutors;

    public static AppExecutors getInstance(){
        if (appExecutors==null){
            appExecutors = new AppExecutors();
        }
        return appExecutors;
    }

    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}

