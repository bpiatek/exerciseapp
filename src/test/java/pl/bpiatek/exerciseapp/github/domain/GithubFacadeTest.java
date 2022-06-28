package pl.bpiatek.exerciseapp.github.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import pl.bpiatek.exerciseapp.github.api.app.AppResponse;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiRequest;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

import java.util.Optional;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@ExtendWith(MockitoExtension.class)
class GithubFacadeTest {

  private static final String TEST_LOGIN = "testLogin";
  private static final String TEST_LOGIN_UPPER_CASE = "TESTLOGIN";
  private final GithubApiFeignClient githubApiFeignClient = mock(GithubApiFeignClient.class);
  private final GithubRepository inMemoryGithubEntityRepository =
      new InMemoryGithubEntityRepository();
  private GithubFacade githubFacade;

  @BeforeEach
  void setUp() {
    githubFacade = new GithubConfiguration().githubFacade(githubApiFeignClient, inMemoryGithubEntityRepository);
  }

  @Test
  void shouldMapGithubApiResponseToAppResponse() {
    // given
    GithubApiRequest request = prepareGithubApiRequest(TEST_LOGIN);
    given(githubApiFeignClient.getUserDetails(request.asString()))
        .willReturn(ResponseEntity.ok(prepareApiResponse(12L)));

    // when
    AppResponse actual = githubFacade.getUserInfo(request);

    // then
    assertThat(actual.getId()).isEqualTo(12L);
  }

  @Test
  void shouldSaveGithubEntityInDbWhenUserInfoRequested() {
    // given
    GithubApiRequest request = prepareGithubApiRequest(TEST_LOGIN);
    given(githubApiFeignClient.getUserDetails(request.asString()))
        .willReturn(ResponseEntity.ok(prepareApiResponse(12L)));

    // when
    githubFacade.getUserInfo(request);

    // then
    Optional<GithubEntity> fromDb = inMemoryGithubEntityRepository.findByLogin(TEST_LOGIN);
    assertThat(fromDb).isPresent();
    assertThat(fromDb.get().getRequestCount()).isEqualTo(1);
  }

  @Test
  void shouldSaveGithubEntityInDbAndCorrectlyIncreaseRequestCountIgnoringLowerOrUpperCase() {
    // given
    GithubApiRequest request = prepareGithubApiRequest(TEST_LOGIN);
    GithubApiRequest requestUppercase = prepareGithubApiRequest(TEST_LOGIN_UPPER_CASE);
    given(githubApiFeignClient.getUserDetails(request.asString()))
        .willReturn(ResponseEntity.ok(prepareApiResponse(12L)));

    // when
    githubFacade.getUserInfo(request);
    githubFacade.getUserInfo(requestUppercase);

    // then
    Optional<GithubEntity> fromDb = inMemoryGithubEntityRepository.findByLogin(TEST_LOGIN);
    assertThat(fromDb).isPresent();
    assertThat(fromDb.get().getRequestCount()).isEqualTo(2);
  }

  private GithubApiRequest prepareGithubApiRequest(String login) {
    return GithubApiRequest.of(login);
  }

  private GithubApiResponse prepareApiResponse(Long id) {
    return GithubApiResponse.builder()
        .id(id)
        .build();
  }
}
