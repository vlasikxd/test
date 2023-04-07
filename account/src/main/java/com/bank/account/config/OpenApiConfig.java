package com.bank.account.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Account Service API",
        description = "API endpoints for interacting with accounts",
        version = "v1",
        contact = @Contact(
                name = "KataAcademy",
                url = "https://kata.academy/",
                email = "info@kata.academy")))
public class OpenApiConfig {
}
