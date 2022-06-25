package pl.bpiatek.exerciseapp.github.api;

import lombok.*;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubApiRequest {
  private final String login;

  public static GithubApiRequest of(String login) {
    return new GithubApiRequest(login);
  }

  public String asString() {
    return login;
  }

}
