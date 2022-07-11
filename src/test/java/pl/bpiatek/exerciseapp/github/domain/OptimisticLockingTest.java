package pl.bpiatek.exerciseapp.github.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bpiatek.exerciseapp.infrastructure.exceptions.StaleStateIdentifiedException;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OptimisticLockingTest {

  private static final String LOGIN = "testLogin";
  @Autowired
  private GithubRepository githubRepository;

  @Test
  void savingEntityInCaseOfConflictShouldResultInError() {
    // given
    GithubEntity inDatabase = githubRepository.save(new GithubEntity(LOGIN));

    // and
    GithubEntity loaded = githubRepository.findByLogin(LOGIN).get();
    loaded.increaseRequestCount();

    // and
    inDatabase.increaseRequestCount();
    githubRepository.save(inDatabase);

    // when
    // then
    assertThatThrownBy(() -> githubRepository.save(loaded))
        .isInstanceOf(StaleStateIdentifiedException.class)
        .hasMessageContaining(LOGIN);
  }
}
