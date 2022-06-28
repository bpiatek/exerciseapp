package pl.bpiatek.exerciseapp.infrastructure;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Bartosz Piatek on 28/06/2022
 */
@Configuration
class OpenApiConfiguration {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(new Info().title("Empik task")
                  .version("v0.0.1")
                  .license(new License()
                               .name("MIT"))
                  .contact(new Contact()
                               .name("Bartosz Piatek")
                               .email("piatekbart@gmail.com"))
        );
  }
}
