package com.mydomomain.silverpay.configuration.rest_doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestDocConfig {

    @Bean
    public GroupedOpenApi panelApi(){

        return GroupedOpenApi.builder()
                .group("panel api V1")
                .pathsToMatch("/api/site/panel/**")
                .build();

    }

    @Bean
    public GroupedOpenApi api(){

        return GroupedOpenApi.builder()
                .group("api V1")
                .pathsToMatch("/api/panel/**")
                .build();

    }
    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
