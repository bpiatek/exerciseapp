package pl.bpiatek.exerciseapp.github.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

import java.math.BigDecimal;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
class GithubCalculationsResolverTest {

  private final GithubCalculationsResolver githubCalculationsResolver = new GithubCalculationsResolver();

  @Test
  void shouldCalculateCorrectlyWhenNumbersAreDividingCompletely() {
    // given
    final GithubApiResponse apiObject = prepareApiObject(2, 4);

    // when
    BigDecimal actual = githubCalculationsResolver.calculate(apiObject);

    // then
    assertThat(actual).isEqualTo("18.0");
  }

  @Test
  void shouldCalculateCorrectlyWhenNumbersAreNotDividingCompletely() {
    // given
    GithubApiResponse apiObject = prepareApiObject(5, 5);

    // when
    BigDecimal actual = githubCalculationsResolver.calculate(apiObject);

    // then
    assertThat(actual).isEqualTo("8.4");
  }

  @Test
  void shouldReturnZeroWhenNumberOfFollowersIsZero() {
    // given
    GithubApiResponse apiObject = prepareApiObject(0, 5);

    // when
    BigDecimal actual = githubCalculationsResolver.calculate(apiObject);

    // then
    assertThat(actual).isEqualTo("0");
  }

  @Test
  void shouldReturnZeroWhenNumberOfPublicReposIsZero() {
    // given
    GithubApiResponse apiObject = prepareApiObject(5, 0);

    // when
    BigDecimal actual = githubCalculationsResolver.calculate(apiObject);

    // then
    assertThat(actual).isEqualTo("0");
  }

  GithubApiResponse prepareApiObject(int followers, int publicRepos) {
    return GithubApiResponse.builder()
        .followers(followers)
        .publicRepos(publicRepos)
        .build();
  }
}
