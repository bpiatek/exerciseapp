package pl.bpiatek.exerciseapp.github.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
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
}
