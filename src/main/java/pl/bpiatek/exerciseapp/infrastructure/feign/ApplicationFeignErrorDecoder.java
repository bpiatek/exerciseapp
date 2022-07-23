package pl.bpiatek.exerciseapp.infrastructure.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import pl.bpiatek.exerciseapp.infrastructure.exceptions.BadRequestException;
import pl.bpiatek.exerciseapp.infrastructure.exceptions.NotFoundException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Slf4j
class ApplicationFeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder errorDecoder = new Default();

  @Override
  public Exception decode(String methodKey, Response response) {
    FeignExceptionMessage message;
    try (InputStream bodyIs = response.body().asInputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      message = mapper.readValue(bodyIs, FeignExceptionMessage.class);
    } catch (IOException e) {
      return new Exception(e.getMessage());
    }

    switch (response.status()) {
      case 400:
        throw new BadRequestException(message.getMessage() != null ? message.getMessage() : "Bad Request");
      case 404:
        log.info("Github user: {} not found.", extractUserNameFromResponse(response));
        throw new NotFoundException(message.getMessage() != null ? message.getMessage() : "Not found");
      default:
        return errorDecoder.decode(methodKey, response);
    }
  }

  private String extractUserNameFromResponse(Response response) {
    String url = response.request().url();
    return url.substring(url.lastIndexOf("/") + 1);
  }
}
