package pl.bpiatek.exerciseapp.github.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import pl.bpiatek.exerciseapp.github.api.app.AppResponse;
import pl.bpiatek.exerciseapp.github.api.app.DatabaseEntryResponse;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiRequest;
import pl.bpiatek.exerciseapp.github.api.feign.GithubApiResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@RequiredArgsConstructor
public class GithubFacade {

  private final GithubApiFeignClient apiFeignClient;
  private final GithubRepository githubRepository;
  private final ApplicationResponseCreator applicationResponseCreator;

  @Transactional
  public AppResponse getUserInfo(GithubApiRequest request) {
    ResponseEntity<GithubApiResponse> githubApiResponse = apiFeignClient.getUserDetails(request.asString());

    githubRepository.findByLogin(request.asString())
        .ifPresentOrElse(
            GithubEntity::increaseRequestCount,
            () -> save(request)
        );

    return applicationResponseCreator.create(githubApiResponse.getBody());
  }

  public List<DatabaseEntryResponse> showDatabaseEntries() {
    return githubRepository.findAll().stream()
        .map(GithubEntity::mapToDto)
        .collect(Collectors.toList());
  }


  private void save(GithubApiRequest request) {
    githubRepository.save(new GithubEntity(request.asString()));
  }
}

