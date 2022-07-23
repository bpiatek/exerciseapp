package pl.bpiatek.exerciseapp.github.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class GithubFacade {

  private final GithubApiFeignClient apiFeignClient;
  private final GithubRepository githubRepository;
  private final ApplicationResponseCreator applicationResponseCreator;

  @Transactional(noRollbackFor = NotFoundException.class)
  public Result getUserInfo(GithubApiRequest request) {
    try {
      return getUserInfoAndUpdateCounter(request);
    } catch (StaleStateIdentifiedException e) {
      return conflictResult(request);
    }
  }

  public List<GithubEntityView> showDatabaseEntries() {
    log.info("Show all database entries.");
    return githubRepository.findAll().stream()
        .map(GithubEntity::mapToDto)
        .toList();
  }

  private GithubSaved getUserInfoAndUpdateCounter(GithubApiRequest request) {
    log.info("User information requested for: {}", request.asString());
    updateCounter(request);
    ResponseEntity<GithubApiResponse> githubApiResponse = apiFeignClient.getUserDetails(request.asString());
    return new GithubSaved(applicationResponseCreator.create(githubApiResponse.getBody()));
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

  private Result conflictResult(GithubApiRequest request) {
    GithubEntity currentState = githubRepository.findByLogin(request.asString()).orElse(null);
    if (currentState == null) {
      return new GithubNotFound();
    }
    log.warn("Stale state identified for: {}. Current version in database is: {}",
             request.asString(), currentState.getVersion());
    return new GithubConflictIdentified(currentState.mapToDto());
  }
}

