package ericwush.model.converter;

import ericwush.model.Ticket;
import ericwush.model.dto.ZendeskRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ZendeskRequestConverter implements Function<ZendeskRequest, Ticket> {

  @Override
  public Ticket apply(final ZendeskRequest request) {
    return new Ticket(request.getBody().getId(), request.getBody().getSubject(), request.getBody().getDescription(),
        request.getBody().getStatus(), request.getBody().getUpdatedAt());
  }

}
