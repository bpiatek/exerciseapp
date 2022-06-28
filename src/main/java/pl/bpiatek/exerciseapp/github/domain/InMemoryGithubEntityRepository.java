package pl.bpiatek.exerciseapp.github.domain;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
class InMemoryGithubEntityRepository implements GithubRepository {

  private final ConcurrentHashMap<String, GithubEntity> store = new ConcurrentHashMap<>();

  @Override
  public GithubEntity save(GithubEntity entity) {
    store.put(entity.getLogin(), entity);
    return entity;
  }

  @Override
  public Optional<GithubEntity> findByLogin(String login) {
    return Optional.ofNullable(store.get(login.toLowerCase()));
  }
}
