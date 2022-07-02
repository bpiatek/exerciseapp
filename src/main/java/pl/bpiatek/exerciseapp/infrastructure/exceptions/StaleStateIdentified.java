package pl.bpiatek.exerciseapp.infrastructure.exceptions;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
@ResponseStatus(PRECONDITION_FAILED)
public class StaleStateIdentified extends RuntimeException {

  private StaleStateIdentified(String login) {
    super(String.format("Github account with login %s is stale", login));
  }

  public static StaleStateIdentified forGithubEntityWithLogin(String login) {
    return new StaleStateIdentified(login);
  }
}
