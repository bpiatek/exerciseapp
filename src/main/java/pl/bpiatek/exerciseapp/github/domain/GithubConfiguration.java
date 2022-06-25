package pl.bpiatek.exerciseapp.github.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Configuration
class GithubConfiguration {

  @Bean
  GithubFacade githubFacade(GithubApiFeignClient githubApiFeignClient) {
    return new GithubFacade(githubApiFeignClient);
  }
}
