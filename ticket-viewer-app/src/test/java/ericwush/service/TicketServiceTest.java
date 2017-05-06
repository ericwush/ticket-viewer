package ericwush.service;

import ericwush.integration.ZendeskApi;
import ericwush.model.Ticket;
import ericwush.model.Tickets;
import ericwush.model.converter.ZendeskRequestConverter;
import ericwush.model.converter.ZendeskRequestsConverter;
import ericwush.model.dto.ZendeskRequest;
import ericwush.model.dto.ZendeskRequests;
import javaslang.control.Try;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static ericwush.helper.TestHelper.assertTicket;
import static ericwush.helper.TestHelper.createZendeskRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

  @Mock
  private ZendeskApi api;
  private TicketService service;

  @Before
  public void setUp() {
    ZendeskRequestConverter requestConverter = new ZendeskRequestConverter();
    ZendeskRequestsConverter requestsConverter = new ZendeskRequestsConverter(requestConverter);
    service = new TicketService(api, requestConverter, requestsConverter);
  }

  @Test
  public void shouldGetTicket() {
    // Given
    when(api.getRequest(100L)).thenReturn(Try.success(createRequest()));

    // When
    Try<Ticket> tryTicket = service.getTicket(100);

    // Then
    assertTrue(tryTicket.isSuccess());
    Ticket ticket = tryTicket.get();
    assertTicket(ticket);
  }

  @Test
  public void shouldHaveExceptionWhenFailToGetRequest() {
    // Given
    when(api.getRequest(100)).thenReturn(Try.failure(new IllegalStateException("failed")));

    // When
    Try<Ticket> tryTicket = service.getTicket(100);

    // Then
    assertTrue(tryTicket.isFailure());
    Throwable cause = tryTicket.getCause();
    assertTrue(cause instanceof IllegalStateException);
    assertThat(cause.getMessage(), is("failed"));
  }

  @Test
  public void shouldGetTickets() {
    // Given
    when(api.getRequests(2)).thenReturn(Try.success(createRequests()));

    // When
    Try<Tickets> tryTickets = service.getTickets(2);

    // Then
    assertTrue(tryTickets.isSuccess());
    Tickets tickets = tryTickets.get();
    assertTrue(tickets.getPreviousPage().isPresent());
    assertThat(tickets.getPreviousPage().get(), is(1L));
    assertTrue(tickets.getNextPage().isPresent());
    assertThat(tickets.getNextPage().get(), is(3L));
    assertThat(tickets.getTickets().length, is(1));
    Ticket ticket = tickets.getTickets()[0];
    assertTicket(ticket);
  }

  private ZendeskRequest createRequest() {
    ZendeskRequest request = new ZendeskRequest();
    ZendeskRequest.Request body = createZendeskRequest();
    request.setBody(body);
    return request;
  }

  private ZendeskRequests createRequests() {
    ZendeskRequests requests = new ZendeskRequests();
    requests.setRequests(Arrays.array(createZendeskRequest()));
    requests.setCount(30);
    requests.setPreviousPage("https://ericwush.zendesk.com/api/v2/requests.json?page=1");
    requests.setNextPage("https://ericwush.zendesk.com/api/v2/requests.json?page=3");
    return requests;
  }

}