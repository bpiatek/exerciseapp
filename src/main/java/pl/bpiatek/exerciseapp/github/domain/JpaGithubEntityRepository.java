package pl.bpiatek.exerciseapp.github.domain;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.Repository;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Primary
interface JpaGithubEntityRepository extends Repository<GithubEntity, String>, GithubRepository {

}
