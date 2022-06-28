package pl.bpiatek.exerciseapp.github;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bpiatek.exerciseapp.github.api.app.AppResponse;
import pl.bpiatek.exerciseapp.github.api.app.DatabaseEntryResponse;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiRequest;
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
  public ResponseEntity<AppResponse> getUserInfoByLogin(@PathVariable String login) {
    AppResponse response = githubFacade.getUserInfo(GithubApiRequest.of(login));
    return ResponseEntity.ok(response);
  }

  @GetMapping("users/database")
  public ResponseEntity<List<DatabaseEntryResponse>> getAllEntriesFromDatabase() {
    List<DatabaseEntryResponse> response = githubFacade.showDatabaseEntries();
    return ResponseEntity.ok(response);
  }
}
