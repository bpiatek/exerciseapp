package pl.bpiatek.exerciseapp.github.domain;

import lombok.RequiredArgsConstructor;
import pl.bpiatek.exerciseapp.github.api.app.GithubView;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@RequiredArgsConstructor
class ApplicationResponseCreator {

  private final GithubCalculationsResolver githubCalculationsResolver;

  GithubView create(GithubApiResponse githubApiResponse) {
    return GithubView.builder()
        .id(githubApiResponse.getId())
        .login(githubApiResponse.getLogin())
        .name(githubApiResponse.getName())
        .type(githubApiResponse.getType())
        .avatarUrl(githubApiResponse.getAvatarUrl())
        .createdAt(githubApiResponse.getCreatedAt())
        .calculations(githubCalculationsResolver.calculate(githubApiResponse))
        .build();
  }
}
