package com.youthclub.cache;

import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.SyncConfigurationBuilder;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;
import org.infinispan.util.concurrent.IsolationLevel;

import java.util.concurrent.TimeUnit;

public class CacheConfigurationBuilder {

    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(30);

    public static final SyncConfigurationBuilder BUILDER = new ConfigurationBuilder()
            .deadlockDetection()
            .enable()
            .invocationBatching()
            .disable()
            .locking()
            .isolationLevel(IsolationLevel.READ_COMMITTED)
            .lockAcquisitionTimeout(TIMEOUT)
            .clustering()
            .cacheMode(CacheMode.LOCAL)
            .sync();

    public static final SyncConfigurationBuilder TREE_BUILDER = new ConfigurationBuilder()
            .deadlockDetection()
            .enable()
            .invocationBatching()
            .enable()
            .locking()
            .isolationLevel(IsolationLevel.READ_COMMITTED)
            .lockAcquisitionTimeout(TIMEOUT)
            .transaction()
            .transactionManagerLookup(new GenericTransactionManagerLookup())
            .clustering()
            .cacheMode(CacheMode.LOCAL)
            .sync();
}
