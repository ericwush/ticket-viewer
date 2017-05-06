package ericwush.controller;

import ericwush.model.Ticket;
import ericwush.model.Tickets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketControllerIntegrationTest {

  @Autowired
  private TicketController controller;

  @Test
  public void shouldGetRequest() {
    // Given

    // When
    ResponseEntity<Ticket> ticketResponse = controller.getTicket(3);

    // Then
    assertThat(ticketResponse.getStatusCode(), is(HttpStatus.OK));
    Ticket ticket = ticketResponse.getBody();
    assertTicket(ticket);
  }

  @Test
  public void shouldGetRequests() {
    // Given

    // When
    ResponseEntity<Tickets> ticketsResponse = controller.getTickets(1);

    // Then
    assertThat(ticketsResponse.getStatusCode(), is(HttpStatus.OK));
    Tickets tickets = ticketsResponse.getBody();
    assertFalse(tickets.getNextPage().isPresent());
    assertFalse(tickets.getPreviousPage().isPresent());
    Ticket ticket = tickets.getTickets()[0];
    assertTicket(ticket);
  }

  private void assertTicket(final Ticket ticket) {
    assertThat(ticket.getId(), is(3L));
    assertThat(ticket.getSubject(), is("Test Ticket"));
    assertThat(ticket.getDescription(), is("This is a test ticket"));
    assertThat(ticket.getStatus(), is("open"));
  }

}
