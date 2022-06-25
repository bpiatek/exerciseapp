package pl.bpiatek.exerciseapp.infrastructure;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bpiatek.exerciseapp.infrastructure.feign.ApplicationFeignErrorDecoder;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Configuration
class ApplicationConfiguration {

  @Bean
  ErrorDecoder errorDecoder() {
    return new ApplicationFeignErrorDecoder();
  }
}
