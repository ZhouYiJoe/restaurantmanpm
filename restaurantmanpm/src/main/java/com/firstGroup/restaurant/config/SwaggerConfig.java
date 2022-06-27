package com.firstGroup.restaurant.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootConfiguration
//开启Swagger2
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(ApiInfo apiInfo,
                         SecurityContext securityContext,
                         SecurityScheme securityScheme) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.firstGroup.restaurant.controller"))
                .build()
                .securityContexts(Collections.singletonList(securityContext))
                .securitySchemes(Collections.singletonList(securityScheme));
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo("餐馆物资管理平台",
                "餐馆物资管理平台的所有API文档",
                "0.0.1-SNAPSHOT",
                null,
                null,
                null,
                null,
                new ArrayList<>());
    }

    @Bean
    public SecurityContext securityContext(SecurityReference securityReference) {
        return SecurityContext
                .builder()
                .securityReferences(Collections.singletonList(securityReference))
                .build();
    }

    @Bean
    public SecurityReference securityReference() {
        AuthorizationScope authorizationScope =
                new AuthorizationScope("global", "accessEverything");
        return new SecurityReference("token",
                new AuthorizationScope[]{authorizationScope});
    }

    @Bean
    public SecurityScheme securityScheme() {
        return new ApiKey("token", "token", "header");
    }
}
