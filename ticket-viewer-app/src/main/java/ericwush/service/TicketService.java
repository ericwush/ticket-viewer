package ericwush.service;

import ericwush.integration.ZendeskApi;
import ericwush.model.Ticket;
import ericwush.model.Tickets;
import ericwush.model.converter.ZendeskRequestConverter;
import ericwush.model.converter.ZendeskRequestsConverter;
import javaslang.control.Try;
import org.springframework.stereotype.Component;

@Component
public class TicketService {

  private final ZendeskApi zendeskApi;
  private final ZendeskRequestConverter requestConverter;
  private final ZendeskRequestsConverter requestsConverter;

  public TicketService(final ZendeskApi zendeskApi, final ZendeskRequestConverter requestConverter,
                       final ZendeskRequestsConverter requestsConverter) {
    this.zendeskApi = zendeskApi;
    this.requestConverter = requestConverter;
    this.requestsConverter = requestsConverter;
  }

  public Try<Ticket> getTicket(final long ticketId) {
    return zendeskApi.getRequest(ticketId).map(zendeskRequest -> requestConverter.apply(zendeskRequest.getBody()));
  }

  public Try<Tickets> getTickets(final long pageId) {
    return zendeskApi.getRequests(pageId).map(requestsConverter);
  }

}
