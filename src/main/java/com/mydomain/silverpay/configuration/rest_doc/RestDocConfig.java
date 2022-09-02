package com.mydomain.silverpay.configuration.rest_doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestDocConfig {


    @Bean
    public GroupedOpenApi panelApi(){

        return GroupedOpenApi.builder()
                .group("V1_site_Admin")
                .addOpenApiCustomiser(new OpenApiCustomiser() {
                    @Override
                    public void customise(OpenAPI openApi) {
//                        openApi.setInfo(new Info().description("KKKK").version("5").set);
                    }
                })
                .displayName("V1_admin_controller")
                .packagesToScan("com.mydomain.silverpay.controller.site.V1.admin")
                .build();

    }

    @Bean
    public GroupedOpenApi api(){

        return GroupedOpenApi.builder()
                .group("api V1")
                .displayName("test controller")
                .packagesToScan("com.mydomain.silverpay.controller.site.V2")
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
