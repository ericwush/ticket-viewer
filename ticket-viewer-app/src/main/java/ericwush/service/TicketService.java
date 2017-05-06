package ericwush.service;

import ericwush.integration.ZendeskApi;
import ericwush.model.Ticket;
import ericwush.model.converter.ZendeskRequestConverter;
import javaslang.control.Try;
import org.springframework.stereotype.Component;

@Component
public class TicketService {

  private final ZendeskApi zendeskApi;
  private final ZendeskRequestConverter requestConverter;

  public TicketService(final ZendeskApi zendeskApi, final ZendeskRequestConverter requestConverter) {
    this.zendeskApi = zendeskApi;
    this.requestConverter = requestConverter;
  }

  public Try<Ticket> getTicket(final long id) {
    return zendeskApi.getRequest(id).map(requestConverter);
  }

}
