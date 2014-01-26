package com.youthclub.cache;

import com.youthclub.util.Reference;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.tree.TreeCache;
import org.infinispan.tree.TreeCacheFactory;

import java.util.concurrent.TimeUnit;

public enum TreeCaches {
    GIS_ADDRESS_CACHE("gisAddressCache", CacheConfigurationBuilder.TREE_BUILDER
            .eviction()
            .maxEntries(150000)
            .strategy(EvictionStrategy.LRU)
            .expiration()
            .lifespan(1000, TimeUnit.MINUTES)
            .maxIdle(100, TimeUnit.MINUTES)
            .wakeUpInterval(5, TimeUnit.MINUTES)
            .build()),
    GIS_COORDINATES_CACHE("gisCoordinatesCache", CacheConfigurationBuilder.TREE_BUILDER
            .eviction()
            .maxEntries(150000)
            .strategy(EvictionStrategy.LRU)
            .expiration()
            .lifespan(1000, TimeUnit.MINUTES)
            .maxIdle(100, TimeUnit.MINUTES)
            .wakeUpInterval(5, TimeUnit.MINUTES)
            .build());


    private final String region;
    private final Reference<EmbeddedCacheManager> cacheManager = new Reference<EmbeddedCacheManager>();
    private final Reference<TreeCache> cache = new Reference<TreeCache>();
    private final Reference<Configuration> configuration = new Reference<Configuration>();

    private TreeCaches(final String region, final Configuration configuration) {
        this.region = region;
        this.configuration.set(configuration);
    }

    public synchronized <K, V> TreeCache<K, V> getCache() {
        return internalGetCache(this.region);
    }

    public synchronized <K, V> TreeCache<K, V> getCache(final String region) {
        return internalGetCache(this.region + region);
    }

    private synchronized <K, V> TreeCache<K, V> internalGetCache(final String region) {
        if (cache.get() == null) {
            final EmbeddedCacheManager manager = getCacheManager();
            this.cache.set(new TreeCacheFactory().createTreeCache(manager.getCache(region, true)));
            if (!manager.isRunning(region)) {
                manager.startCaches(region);
            }
            while (!manager.isRunning(region)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //
                }
            }
        }
        return cache.get();
    }

    public synchronized EmbeddedCacheManager getCacheManager() {
        if (cacheManager.get() == null) {
            final EmbeddedCacheManager manager = new DefaultCacheManager(GlobalCacheConfiguration.GLOBAL_CONFIGURATION, configuration.get());
            this.cacheManager.set(manager);
        }
        return cacheManager.get();
    }

    public String getRegion() {
        return region;
    }

    public static synchronized void destroy() {
        for (final TreeCaches cache : values()) {
            cache.configuration.set(null);
            cache.cache.set(null);
            final EmbeddedCacheManager cacheManager = cache.cacheManager.set(null);
            if (cacheManager != null && cacheManager.getStatus().stopAllowed()) {
                cacheManager.stop();
            }
        }
    }
}
