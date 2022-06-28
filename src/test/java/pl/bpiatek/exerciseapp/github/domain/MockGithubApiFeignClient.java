package pl.bpiatek.exerciseapp.github.domain;

import static org.mockito.BDDMockito.given;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

/**
 * Created by Bartosz Piatek on 28/06/2022
 */
@Component
public class MockGithubApiFeignClient {

  @MockBean
  private GithubApiFeignClient githubApiFeignClient;

  public void mockApiCallToGetUserDetails(GithubApiResponse githubApiResponse) {
    given(githubApiFeignClient.getUserDetails(githubApiResponse.getLogin()))
        .willReturn(ResponseEntity.ok(githubApiResponse));
  }

}
