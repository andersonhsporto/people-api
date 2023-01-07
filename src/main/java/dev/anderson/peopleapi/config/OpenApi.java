package dev.anderson.peopleapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApi {

  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("People API")
            .description("People management API")
            .version("v1")
            .contact(
                new Contact()
                    .name("Anderson")
                    .email("anderson.higo2@gmail.com")
                    .url("https://github.com/andersonhsporto")
                )
            );
  }


}
