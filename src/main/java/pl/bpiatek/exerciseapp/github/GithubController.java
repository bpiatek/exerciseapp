package pl.bpiatek.exerciseapp.github;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bpiatek.exerciseapp.github.api.GithubApiRequest;
import pl.bpiatek.exerciseapp.github.api.GithubApiResponse;
import pl.bpiatek.exerciseapp.github.domain.GithubFacade;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
class GithubController {

  private final GithubFacade githubFacade;

  @GetMapping("users/{login}")
  ResponseEntity<GithubApiResponse> test(@PathVariable String login) {
    GithubApiResponse response = githubFacade.getUserInfo(GithubApiRequest.of(login));
    return ResponseEntity.ok(response);
  }
}
