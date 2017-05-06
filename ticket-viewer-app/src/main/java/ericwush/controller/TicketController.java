package ericwush.controller;

import ericwush.model.Ticket;
import ericwush.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

  private final TicketService ticketService;
  private final ResponseMapper responseMapper;

  public TicketController(final TicketService ticketService, final ResponseMapper responseMapper) {
    this.ticketService = ticketService;
    this.responseMapper = responseMapper;
  }

  @SuppressWarnings("unchecked")
  @RequestMapping("/tickets/{ticketId}")
  public ResponseEntity<Ticket> getTicket(@PathVariable long ticketId) {
    return (ResponseEntity<Ticket>) responseMapper.apply(ticketService.getTicket(ticketId));
  }

}
