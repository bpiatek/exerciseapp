package pl.bpiatek.exerciseapp.github.api.app;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Builder
@Getter
public class AppResponse {

  private Long id;
  private String login;
  private String name;
  private String type;
  private String avatarUrl;
  private LocalDateTime createdAt;
  private BigDecimal calculations;
}
