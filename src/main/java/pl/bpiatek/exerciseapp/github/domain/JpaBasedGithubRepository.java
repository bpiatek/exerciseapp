package pl.bpiatek.exerciseapp.github.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.repository.Repository;
import pl.bpiatek.exerciseapp.infrastructure.exceptions.StaleStateIdentified;

import java.util.List;
import java.util.Optional;

/**
 * Created by Bartosz Piatek on 02/07/2022
 */
@RequiredArgsConstructor
class JpaBasedGithubRepository implements GithubRepository {

  private final JpaGithubRepository githubRepository;

  @Override
  public GithubEntity save(GithubEntity entity) {
    try {
      return githubRepository.save(entity);
    } catch (OptimisticLockingFailureException ex) {
      throw StaleStateIdentified.forGithubEntityWithLogin(entity.getLogin());
    }
  }

  @Override
  public Optional<GithubEntity> findByLogin(String login) {
    return githubRepository.findByLogin(login);
  }

  @Override
  public List<GithubEntity> findAll() {
    return githubRepository.findAll();
  }
}

interface JpaGithubRepository extends Repository<GithubEntity, String> {
  GithubEntity save(GithubEntity entity);
  Optional<GithubEntity> findByLogin(String login);
  List<GithubEntity> findAll();
}
