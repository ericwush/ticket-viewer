package ericwush.service;

import ericwush.integration.ZendeskApi;
import ericwush.model.Ticket;
import ericwush.model.converter.ZendeskRequestConverter;
import ericwush.model.dto.ZendeskRequest;
import javaslang.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

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
    ZendeskRequestConverter converter = new ZendeskRequestConverter();
    service = new TicketService(api, converter);
  }

  @Test
  public void shouldGetTicket() {
    // Given
    when(api.getRequest(100L)).thenReturn(Try.success(getRequest()));

    // When
    Try<Ticket> tryTicket = service.getTicket(100);

    // Then
    assertTrue(tryTicket.isSuccess());
    Ticket ticket = tryTicket.get();
    assertThat(ticket.getId(), is(100L));
    assertThat(ticket.getSubject(), is("Can't login"));
    assertThat(ticket.getDescription(), is("description"));
    assertThat(ticket.getStatus(), is("Open"));
    assertThat(ticket.getUpdatedAt(), is(LocalDateTime.of(2017, 5, 1, 11, 10, 00)));
  }

  @Test
  public void shouldHaveExceptionWhenFailToGetRequest() {
    // Given
    when(api.getRequest(100L)).thenReturn(Try.failure(new IllegalStateException("failed")));

    // When
    Try<Ticket> tryTicket = service.getTicket(100);

    // Then
    assertTrue(tryTicket.isFailure());
    Throwable cause = tryTicket.getCause();
    assertTrue(cause instanceof IllegalStateException);
    assertThat(cause.getMessage(), is("failed"));
  }

  private ZendeskRequest getRequest() {
    ZendeskRequest request = new ZendeskRequest();
    request.setId(100L);
    request.setAssigneeId(1999L);
    request.setOrganizationId(2000L);
    request.setRequesterId(3000L);
    request.setDescription("description");
    request.setStatus("Open");
    request.setSubject("Can't login");
    request.setUrl("http://someurl");
    request.setCreatedAt(LocalDateTime.of(2017, 5, 1, 10, 00, 00));
    request.setUpdatedAt(LocalDateTime.of(2017, 5, 1, 11, 10, 00));
    return request;
  }

}