package pl.bpiatek.exerciseapp.github.api.app;

import lombok.Value;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
@Value
public class GithubEntityView {

  String login;
  Integer requestCount;
  Long version;
}
