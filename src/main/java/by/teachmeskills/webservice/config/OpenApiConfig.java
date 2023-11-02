package by.teachmeskills.webservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicCategoryApi() {
        return GroupedOpenApi.builder()
                .group("categories")
                .pathsToMatch("/**/categories/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicProductApi() {
        return GroupedOpenApi.builder()
                .group("products")
                .pathsToMatch("/**/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/**/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicOrderApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/**/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicShopCartApi() {
        return GroupedOpenApi.builder()
                .group("cart")
                .pathsToMatch("/**/cart/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application.description}") String appDescription,
                                 @Value("${application.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Eshop")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact().name("TeachMeSkills")
                                .email("tms@gmail.com")))
                .servers(List.of(new Server().url("http://localhost:8080")
                        .description("Dev service")));
    }
}