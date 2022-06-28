package pl.bpiatek.exerciseapp.github.domain;

import static java.math.BigDecimal.valueOf;

import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
class GithubCalculationsResolver {

  BigDecimal calculate(GithubApiResponse githubApiResponse) {
    if (followersOrPrivateReposCountIsZeroOrNull(githubApiResponse)) {
      return BigDecimal.ZERO;
    }

    //    6 / liczba_followers
    BigDecimal divide = valueOf(6).divide(valueOf(githubApiResponse.getFollowers()), 1, RoundingMode.CEILING);
    //    2 + liczba_public_repos
    BigDecimal add = valueOf(2).add(valueOf(githubApiResponse.getPublicRepos()));

    // 6 / liczba_followers * (2 + liczba_public_repos).
    return divide.multiply(add);
  }

  private boolean followersOrPrivateReposCountIsZeroOrNull(GithubApiResponse githubApiResponse) {
    return (githubApiResponse.getFollowers() == null || githubApiResponse.getFollowers() == 0)
           ||
           (githubApiResponse.getPublicRepos() == null || githubApiResponse.getPublicRepos() == 0);
  }
}
