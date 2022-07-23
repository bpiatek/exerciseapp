package pl.bpiatek.exerciseapp.infrastructure.feign;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Configuration
class FeignConfiguration {

  @Bean
  ErrorDecoder errorDecoder() {
    return new ApplicationFeignErrorDecoder();
  }
}
