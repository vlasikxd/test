package com.bank.publicinfo.config;

import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class SwaggerConfig {

    static {
        final var swaggerSchema = new Schema<LocalTime>();

        swaggerSchema.example(
                LocalTime.now()
                        .format(
                                DateTimeFormatter.ofPattern("HH:mm:ss")
                        )
        );

        SpringDocUtils.getConfig()
                .replaceWithSchema(
                        LocalTime.class, swaggerSchema
                );
    }
}
