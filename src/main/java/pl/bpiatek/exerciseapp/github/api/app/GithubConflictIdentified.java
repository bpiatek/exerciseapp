package pl.bpiatek.exerciseapp.github.api.app;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
@RequiredArgsConstructor
public final class GithubConflictIdentified implements Result {
  private final GithubEntityView currentState;

  public Optional<GithubEntityView> currentState() {
    return Optional.ofNullable(currentState);
  }
}
