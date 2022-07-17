package pl.bpiatek.exerciseapp.github;

import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bpiatek.exerciseapp.github.api.app.*;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiRequest;
import pl.bpiatek.exerciseapp.github.api.app.GithubConflictIdentified;
import pl.bpiatek.exerciseapp.github.domain.GithubFacade;

import java.util.List;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
class GithubController implements OpenApiGithubController {

  private final GithubFacade githubFacade;

  @GetMapping("users/{login}")
  public ResponseEntity<?> getUserInfoByLogin(@PathVariable String login) {
    Result result = githubFacade.getUserInfo(GithubApiRequest.of(login));
    return buildResponseFrom(result);
  }

  @GetMapping("users/database")
  public ResponseEntity<List<GithubEntityView>> getAllEntriesFromDatabase() {
    List<GithubEntityView> response = githubFacade.showDatabaseEntries();
    return ResponseEntity.ok(response);
  }

  private ResponseEntity<?> buildResponseFrom(Result result) {
    if (result instanceof GithubSaved githubSaved) {
      return ResponseEntity.ok(githubSaved.getView());
    } else if (result instanceof GithubNotFound) {
      return ResponseEntity.notFound().build();
    } else if (result instanceof GithubConflictIdentified githubConflictIdentified) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(githubConflictIdentified
                    .currentState()
                    .orElse(null));
    } else {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
