package pl.bpiatek.exerciseapp.github;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.bpiatek.exerciseapp.github.api.app.GithubEntityView;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;
import pl.bpiatek.exerciseapp.github.domain.GithubFacade;
import pl.bpiatek.exerciseapp.github.domain.MockGithubApiFeignClient;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by Bartosz Piatek on 28/06/2022
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class GithubControllerIT {

  private static final Long ID = 1L;
  private static final String USER_LOGIN = "test";
  private static final String USER_NAME = "someUser";
  private static final String USER_TYPE = "user";
  private static final String AVATAR_URL = "https://some.address";
  private static final LocalDateTime CREATED_AT = LocalDateTime.of(2022, 11, 12, 12,15,30);

  @Autowired(required = false)
  private MockMvc mockMvc;

  @Autowired
  private MockGithubApiFeignClient mockGithubApiFeignClient;

  @Autowired
  private GithubFacade githubFacade;

  @Test
  void shouldCorrectlyReturnUserFromGithubApiResponseAndSaveItInDatabase() throws Exception {
    // given
    GithubApiResponse githubApiResponse = GithubApiResponse.builder()
        .id(ID)
        .login(USER_LOGIN)
        .name(USER_NAME)
        .type(USER_TYPE)
        .avatarUrl(AVATAR_URL)
        .createdAt(CREATED_AT)
        .publicRepos(2)
        .followers(2)
        .build();
    mockGithubApiFeignClient.mockApiCallToGetUserDetails(githubApiResponse);

    // when
    ResultActions resultActions = mockMvc.perform(get("/users/" + USER_LOGIN));

    // then
    resultActions.andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.login", is(USER_LOGIN)))
        .andExpect(jsonPath("$.name", is(USER_NAME)))
        .andExpect(jsonPath("$.type", is(USER_TYPE)))
        .andExpect(jsonPath("$.avatarUrl", is(AVATAR_URL)))
        .andExpect(jsonPath("$.createdAt", is(CREATED_AT.toString())))
        .andExpect(jsonPath("$.calculations", is(12.0)))
        .andDo(print());

    // and
    Optional<GithubEntityView> containsEntry = githubFacade.showDatabaseEntries()
        .stream().filter(entry -> entry.getLogin().equals(USER_LOGIN))
        .findAny();
    assertThat(containsEntry).isPresent();
  }
}
