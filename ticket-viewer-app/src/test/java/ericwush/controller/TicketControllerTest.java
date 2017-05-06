package ericwush.controller;

import ericwush.integration.ZendeskObjectMapper;
import ericwush.model.Ticket;
import ericwush.model.Tickets;
import ericwush.service.TicketService;
import javaslang.control.Try;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private TicketService service;
  @Autowired
  private ZendeskObjectMapper objectMapper;

  @Test
  public void getTicketShouldReturnOkResponse() throws Exception {
    // Given
    Ticket ticket = new Ticket(100L, "subject", "description", "open", LocalDateTime.now());
    when(service.getTicket(100L)).thenReturn(Try.success(ticket));
    String expected = objectMapper.writeValueAsString(ticket);

    // When

    // Then
    mvc.perform(get("/api/tickets/100").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(expected));
  }

  @Test
  public void getTicketShouldReturnResponseForHttpStatusCodeException() throws Exception {
    // Given
    HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "RecordNotFound");
    when(service.getTicket(100L)).thenReturn(Try.failure(exception));

    // When

    // Then
    mvc.perform(get("/api/tickets/100").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound()).andExpect(content().string("Zendesk 404 RecordNotFound"));
  }

  @Test
  public void getTicketShouldReturnResponseForOtherException() throws Exception {
    // Given
    IllegalStateException exception = new IllegalStateException("connection refused");
    when(service.getTicket(100L)).thenReturn(Try.failure(exception));

    // When

    // Then
    mvc.perform(get("/api/tickets/100").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError()).andExpect(content().string("Zendesk connection refused"));
  }

  @Test
  public void getTicketsShouldReturnOkResponseForTickets() throws Exception {
    // Given
    Ticket ticket = new Ticket(100L, "subject", "description", "open", LocalDateTime.now());
    Tickets tickets = new Tickets(Arrays.array(ticket), Optional.of(2L), Optional.empty());
    when(service.getTickets(1L)).thenReturn(Try.success(tickets));
    String expected = objectMapper.writeValueAsString(tickets);

    // When

    // Then
    mvc.perform(get("/api/tickets?page=1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(expected));
  }

  @Test
  public void getTicketsShouldReturnOkResponseWhenNoTicket() throws Exception {
    // Given
    Tickets tickets = new Tickets(new Ticket[0], Optional.empty(), Optional.empty());
    when(service.getTickets(10L)).thenReturn(Try.success(tickets));
    String expected = objectMapper.writeValueAsString(tickets);

    // When

    // Then
    mvc.perform(get("/api/tickets?page=10").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(expected));
  }

  @Test
  public void getTicketsShouldReturnResponseForOtherException() throws Exception {
    // Given
    IllegalStateException exception = new IllegalStateException("connection refused");
    when(service.getTickets(1L)).thenReturn(Try.failure(exception));

    // When

    // Then
    mvc.perform(get("/api/tickets?page=1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError()).andExpect(content().string("Zendesk connection refused"));
  }

}