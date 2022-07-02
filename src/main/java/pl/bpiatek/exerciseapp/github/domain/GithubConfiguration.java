package pl.bpiatek.exerciseapp.github.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Configuration
class GithubConfiguration {

  @Bean
  GithubFacade githubFacade(
      GithubApiFeignClient githubApiFeignClient,
      GithubRepository githubRepository
  ) {
    GithubCalculationsResolver githubCalculationsResolver = new GithubCalculationsResolver();
    ApplicationResponseCreator applicationResponseCreator = new ApplicationResponseCreator(githubCalculationsResolver);

    return new GithubFacade(
        githubApiFeignClient,
        githubRepository,
        applicationResponseCreator
    );
  }

  @Bean
  GithubRepository githubRepository(JpaGithubRepository jpaGithubRepository) {
    return new JpaBasedGithubRepository(jpaGithubRepository);
  }
}
