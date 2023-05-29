package com.bank.antifraud.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

@Configuration
@EnableCaching
public class EhCacheConfig {
    @Bean
    public CacheManager ehCacheManager() {
        final CachingProvider cachingProvider = Caching.getCachingProvider();
        final CacheManager cacheManager = cachingProvider.getCacheManager();
        final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
        config.setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_DAY))
                .setStatisticsEnabled(true);
        cacheManager.createCache("transfer-cache", config);
        return cacheManager;
    }
}
