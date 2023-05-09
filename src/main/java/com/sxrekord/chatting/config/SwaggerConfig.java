package com.sxrekord.chatting.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @author Rekord
 * @date 2023/4/14 9:28
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()))
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sxrekord.chatting.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public SecurityScheme apiKey() {
        return new ApiKey(HttpHeaders.AUTHORIZATION, "Authorization", In.HEADER.toString());
    }

    @Bean
    public SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(new SecurityReference(HttpHeaders.AUTHORIZATION, new AuthorizationScope[0])))
                .build();
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Chatting 接口文档")
                .description("Chatting on Web")
                .contact(new Contact("Rekord", "https://github.com/Crazyokd", "wildrekord@gmail.com"))
                .version("v0.4.2")
                .build();
    }
}