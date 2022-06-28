package pl.bpiatek.exerciseapp.github.domain;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@FeignClient(name = "githubClient", url = "${github.api.url}")
interface GithubApiFeignClient {

  @GetMapping(value = "/users/{login}")
  ResponseEntity<GithubApiResponse> getUserDetails(@PathVariable String login);
}
