package com.youthclub.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.MINUTES;

@Singleton
@LocalBean
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ExecutorManager {

    private ExecutorService background;
    private ExecutorService producer;

    @PostConstruct
    public void create() {
        producer = Executors.newCachedThreadPool();
    }

    @PreDestroy
    public void destroy() {
        if (background != null) {
            background.shutdown();
        }
        producer.shutdown();
    }

    public synchronized ExecutorService getBackgroundExecutor() {
        if (background == null) {
            background = new ThreadPoolExecutor(4, 20, 60L, MINUTES, new LinkedBlockingDeque<Runnable>());
        }
        return background;
    }

    public ExecutorService getProducerExecutor() {
        return producer;
    }
}