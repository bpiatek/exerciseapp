package pl.bpiatek.exerciseapp.infrastructure.exceptions;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Created by Bartosz Piatek on 23/07/2022
 */
@ControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(GeneralException.class)
  public ResponseEntity<ErrorDto> generalExceptionHandler(GeneralException ex) {
    final ErrorDto error = ErrorDto.builder()
        .timestamp(LocalDateTime.now())
        .status(ex.getHttpStatus().value())
        .error(ex.getHttpStatus().getReasonPhrase())
        .message(ex.getMessage())
        .path(ex.getPath())
        .build();

    return new ResponseEntity<>(error, ex.getHttpStatus());
  }


  @Value
  @Builder
  static class ErrorDto {
    LocalDateTime timestamp;
    Integer status;
    String error;
    String message;
    String path;
  }
}
