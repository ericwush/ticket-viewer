package ericwush.model.converter;

import ericwush.model.Ticket;
import ericwush.model.dto.ZendeskRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ZendeskRequestConverter implements Function<ZendeskRequest.Request, Ticket> {

  @Override
  public Ticket apply(final ZendeskRequest.Request request) {
    return new Ticket(request.getId(), request.getSubject(), request.getDescription(), request.getStatus(),
        request.getUpdatedAt());
  }

}
