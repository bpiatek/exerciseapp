package pl.bpiatek.exerciseapp.github.domain;

import java.util.List;
import java.util.Optional;

/**
 * Created by Bartosz Piatek on 27/06/2022
 */
interface GithubRepository {

  GithubEntity save(GithubEntity entity);

  Optional<GithubEntity> findByLogin(String login);

  List<GithubEntity> findAll();
}
