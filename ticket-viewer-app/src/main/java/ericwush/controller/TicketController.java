package ericwush.controller;

import ericwush.model.Ticket;
import ericwush.model.Tickets;
import ericwush.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
public class TicketController {

  private final TicketService ticketService;
  private final ResponseMapper responseMapper;

  public TicketController(final TicketService ticketService, final ResponseMapper responseMapper) {
    this.ticketService = ticketService;
    this.responseMapper = responseMapper;
  }

  @RequestMapping("/tickets/{ticketId}")
  public ResponseEntity<Ticket> getTicket(@PathVariable long ticketId) {
    return (ResponseEntity<Ticket>) responseMapper.apply(ticketService.getTicket(ticketId));
  }

  @RequestMapping("/tickets")
  public ResponseEntity<Tickets> getTickets(@RequestParam long page) {
    return (ResponseEntity<Tickets>) responseMapper.apply(ticketService.getTickets(page));
  }

}
