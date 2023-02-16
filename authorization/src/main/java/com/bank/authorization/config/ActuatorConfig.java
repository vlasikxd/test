package com.bank.authorization.config;

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
 * Конфигурация actuator.
 */
@Configuration
@PropertySource("classpath:application-info.yaml")
public class ActuatorConfig {

    @Value("${version}")
    private String version;

    @Value("${name}")
    private String artifactId;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * @return {@link MapInfoContributor}
     */
    @Bean
    public InfoContributor getInfoContributor() {
        final Map<String, Object> variables = Map.of(
                "version", version,
                "name", artifactId,
                "path", contextPath,
                "startDateTime", LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        return new MapInfoContributor(variables);
    }
}
