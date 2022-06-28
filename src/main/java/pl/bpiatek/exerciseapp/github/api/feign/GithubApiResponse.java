package pl.bpiatek.exerciseapp.github.api.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Builder
@Getter
public class GithubApiResponse {

  private Long id;
  private String login;
  private String name;
  private String type;
  @JsonProperty("avatar_url")
  private String avatarUrl;
  @JsonProperty("created_at")
  private LocalDateTime createdAt;
  private Integer followers;
  @JsonProperty("public_repos")
  private Integer publicRepos;
}
