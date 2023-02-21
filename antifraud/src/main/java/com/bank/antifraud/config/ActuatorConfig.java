package com.bank.antifraud.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.info.MapInfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * Конфигурация actuator
 */
@Configuration
@PropertySource("/application-info.yaml")
public class ActuatorConfig {

    @Value("${server.servlet.context-path}")
    private String appContextWebPath;

    @Value("${name}")
    private String appName;

    @Value("${version}")
    private String appVersion;

    /**
     * @return {@link MapInfoContributor}
     */
    @Bean
    public InfoContributor getInfoContributor() {
        final Map<String, Object> properties = Map.of(
                "name", appName,
                "version", appVersion,
                "path", appContextWebPath,
                "startDateTime", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
        );

        return new MapInfoContributor(properties);
    }
}
