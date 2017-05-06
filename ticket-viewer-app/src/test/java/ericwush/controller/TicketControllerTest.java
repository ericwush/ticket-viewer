package ericwush.controller;

import ericwush.model.Ticket;
import ericwush.service.TicketService;
import javaslang.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

  @Mock
  private TicketService service;
  private ResponseMapper responseMapper;
  private TicketController controller;

  @Before
  public void setUp() {
    responseMapper = new ResponseMapper();
    controller = new TicketController(service, responseMapper);
  }

  @Test
  public void shouldReturnOkResponse() {
    // Given
    Ticket ticket = new Ticket(100L, "subject", "description", "open", LocalDateTime.now());
    when(service.getTicket(100L)).thenReturn(Try.success(ticket));

    // When
    ResponseEntity response = controller.getTicket(100L);

    // Then
    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody(), is(ticket));
  }

  @Test
  public void shouldReturnResponseForHttpStatusCodeException() {
    // Given
    HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "RecordNotFound");
    when(service.getTicket(100L)).thenReturn(Try.failure(exception));


    // When
    ResponseEntity response = controller.getTicket(100L);

    // Then
    assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    assertThat(response.getBody(), is("Zendesk 404 RecordNotFound"));
  }

  @Test
  public void shouldReturnResponseForOtherException() {
    // Given
    IllegalStateException exception = new IllegalStateException("connection refused");
    when(service.getTicket(100L)).thenReturn(Try.failure(exception));


    // When
    ResponseEntity response = controller.getTicket(100L);

    // Then
    assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    assertThat(response.getBody(), is("Zendesk connection refused"));
  }

}