package ericwush.controller;

import javaslang.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.function.Function;

@Component
public class ResponseMapper implements Function<Try<?>, ResponseEntity> {

  @SuppressWarnings("unchecked")
  @Override
  public ResponseEntity apply(final Try<?> result) {
    return
        result
            .map(response -> new ResponseEntity(response, HttpStatus.OK))
            .recover(exception -> {
              if (exception instanceof HttpStatusCodeException) {
                return new ResponseEntity("Zendesk " + exception.getMessage(),
                    ((HttpStatusCodeException) exception).getStatusCode());
              } else {
                return new ResponseEntity("Zendesk " + exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
              }
            })
            .get();
  }

}
