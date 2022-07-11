package pl.bpiatek.exerciseapp.github.api.app;

import lombok.RequiredArgsConstructor;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
@RequiredArgsConstructor
public class GithubSaved implements Result {
  private final GithubView view;

  public GithubView getView() {
    return view;
  }
}
