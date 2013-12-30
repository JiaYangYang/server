package com.youthclub.cache;

import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;

public class GlobalCacheConfiguration {
    public static final GlobalConfiguration GLOBAL_CONFIGURATION = new GlobalConfigurationBuilder()
            .clusteredDefault()
            .globalJmxStatistics()
            .jmxDomain("com.youthclub.server")
            .allowDuplicateDomains(true)
            .asyncListenerExecutor()
            .addProperty("maxThreads", "2")
            .build();
}
