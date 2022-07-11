package pl.bpiatek.exerciseapp.github.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import pl.bpiatek.exerciseapp.github.api.app.*;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiRequest;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;
import pl.bpiatek.exerciseapp.infrastructure.exceptions.NotFoundException;
import pl.bpiatek.exerciseapp.infrastructure.exceptions.StaleStateIdentifiedException;

import java.util.List;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@RequiredArgsConstructor
public class GithubFacade {

  private final GithubApiFeignClient apiFeignClient;
  private final GithubRepository githubRepository;
  private final ApplicationResponseCreator applicationResponseCreator;

  @Transactional(noRollbackFor = NotFoundException.class)
  public Result getUserInfo(GithubApiRequest request) {
    try {
      updateCounter(request);
      ResponseEntity<GithubApiResponse> githubApiResponse = apiFeignClient.getUserDetails(request.asString());
      return new GithubSaved(applicationResponseCreator.create(githubApiResponse.getBody()));
    } catch (StaleStateIdentifiedException e) {
      GithubEntity currentState = githubRepository.findByLogin(request.asString()).orElse(null);
      if(currentState == null) {
        return new GithubNotFound();
      }
      return new GithubConflictIdentified(currentState.mapToDto());
    }
  }

  public List<GithubEntityView> showDatabaseEntries() {
    return githubRepository.findAll().stream()
        .map(GithubEntity::mapToDto)
        .toList();
  }

  private void updateCounter(GithubApiRequest request) {
    githubRepository.findByLogin(request.asString())
        .ifPresentOrElse(
            GithubEntity::increaseRequestCount,
            () -> save(request)
        );
  }
  private void save(GithubApiRequest request) {
    githubRepository.save(new GithubEntity(request.asString()));
  }
}

