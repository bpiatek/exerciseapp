package pl.bpiatek.exerciseapp.infrastructure.feign;

import lombok.Getter;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Getter
public class FeignExceptionMessage {
  private String timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
}
