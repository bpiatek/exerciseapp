package pl.bpiatek.exerciseapp.infrastructure.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by Bartosz Piatek on 23/07/2022
 */
@Getter
public class GeneralException extends RuntimeException {

  private final String message;
  private final HttpStatus httpStatus;

  private final String path;

  public GeneralException(int status, String message, String path) {
    this.message = message;
    this.httpStatus = HttpStatus.valueOf(status);
    this.path = path;
  }
}
