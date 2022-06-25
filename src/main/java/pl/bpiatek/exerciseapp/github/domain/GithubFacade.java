package pl.bpiatek.exerciseapp.github.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import pl.bpiatek.exerciseapp.github.api.GithubApiRequest;
import pl.bpiatek.exerciseapp.github.api.GithubApiResponse;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@RequiredArgsConstructor
public class GithubFacade {

  private final GithubApiFeignClient apiFeignClient;

  public GithubApiResponse getUserInfo(GithubApiRequest request) {
    ResponseEntity<GithubApiResponse> response = apiFeignClient.getUserDetails(request.asString());
    return response.getBody();
  }
}

