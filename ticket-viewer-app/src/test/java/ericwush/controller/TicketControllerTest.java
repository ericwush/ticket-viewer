package ericwush.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ericwush.model.Ticket;
import ericwush.service.TicketService;
import javaslang.control.Try;
import org.junit.Before;
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
  private ObjectMapper objectMapper;

  @Before
  public void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void shouldReturnOkResponse() throws Exception {
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
  public void shouldReturnResponseForHttpStatusCodeException() throws Exception {
    // Given
    HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "RecordNotFound");
    when(service.getTicket(100L)).thenReturn(Try.failure(exception));

    // When

    // Then
    mvc.perform(get("/api/tickets/100").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound()).andExpect(content().string("Zendesk 404 RecordNotFound"));
  }

  @Test
  public void shouldReturnResponseForOtherException() throws Exception {
    // Given
    IllegalStateException exception = new IllegalStateException("connection refused");
    when(service.getTicket(100L)).thenReturn(Try.failure(exception));

    // When

    // Then
    mvc.perform(get("/api/tickets/100").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError()).andExpect(content().string("Zendesk connection refused"));
  }

}