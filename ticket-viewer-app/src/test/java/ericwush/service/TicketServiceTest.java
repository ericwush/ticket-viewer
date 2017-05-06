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
    assertTicket(ticket);
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
    ZendeskRequest.Request body = createZendeskRequest();
    request.setBody(body);
    return request;
  }

}