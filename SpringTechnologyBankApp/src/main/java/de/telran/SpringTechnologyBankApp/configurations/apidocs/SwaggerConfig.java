package de.telran.SpringTechnologyBankApp.configurations.apidocs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "basicAuth",
        scheme = "basic",
        description = "Пользователь - superUserApp, Пароль - 1111")
@OpenAPIDefinition(
        servers = {@Server(url = "http://localhost:8080", description = "Development")},
        security = {},
        info = @Info(
                title = "SpringTechnologyBankApplication",
                version = "1.0.0",
                contact = @Contact(
                        name = "Franchuk Dmytro",
                        email = "franchuk.df@gmail.com",
                        url = "https://github.com/DmytroFranchuk/SpringTechnology.git"),
                license = @License(
                        name = "Design assignment for development",
                        url = "https://docs.google.com/document/d/1rhq_oa6vmiX9WnZrbnNDAJP2kmz8DTeDQHWQzX_idr8/edit?usp=sharing"),
                description = "Final qualifying work",
                termsOfService = "https://docs.google.com/document/d/1rhq_oa6vmiX9WnZrbnNDAJP2kmz8DTeDQHWQzX_idr8/edit?usp=sharing",
                summary = "Учебный проект web-сервиса",
                extensions = {})
)
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi managersApi() {
        return GroupedOpenApi.builder()
                .group("managers")
                .pathsToMatch("/api/v1/managers/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productsApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch("/api/v1/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi clientsApi() {
        return GroupedOpenApi.builder()
                .group("clients")
                .pathsToMatch("/api/v1/clients/**")
                .build();
    }

    @Bean
    public GroupedOpenApi accountsApi() {
        return GroupedOpenApi.builder()
                .group("accounts")
                .pathsToMatch("/api/v1/accounts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi agreementsApi() {
        return GroupedOpenApi.builder()
                .group("agreements")
                .pathsToMatch("/api/v1/agreements/**")
                .build();
    }

    @Bean
    public GroupedOpenApi transactionsApi() {
        return GroupedOpenApi.builder()
                .group("transactions")
                .pathsToMatch("/api/v1/transactions/**")
                .build();
    }

    @Bean
    public GroupedOpenApi UserRegistrationApi() {
        return GroupedOpenApi.builder()
                .group("registration")
                .pathsToMatch("/api/v1/registration/users/**")
                .build();
    }
}


//    private static final String TITLE = "SpringTechnologyBankApp";
//    private static final String VERSION = "1.0.0";
//    private static final String DESCRIPTION = "Документация по проекту";
//    private static final String AUTH_NAME = "BasicAuth";

//    @Bean
//    public Info getInfo() {
//        return new Info()
//                .title(TITLE)
//                .version(VERSION)
//                .description(DESCRIPTION);
//    }

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components().addSecuritySchemes("Telran", new io.swagger.v3.oas.models.security.SecurityScheme()
//                        .name(AUTH_NAME)
//                        .scheme("basic")
//                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
//                        .in(SecurityScheme.In.HEADER)))
//                .addSecurityItem(new SecurityRequirement().addList(AUTH_NAME)).info(getInfo());
//    }
