package pl.bpiatek.exerciseapp.infrastructure.exceptions;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
public class StaleStateIdentifiedException extends RuntimeException {

  private StaleStateIdentifiedException(String login) {
    super(String.format("Github account with login %s is stale", login));
  }

  public static StaleStateIdentifiedException forGithubEntityWithLogin(String login) {
    return new StaleStateIdentifiedException(login);
  }
}
