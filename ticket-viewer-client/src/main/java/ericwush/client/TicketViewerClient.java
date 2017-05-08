package ericwush.client;

import javaslang.control.Try;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class TicketViewerClient {

  private final RestTemplate restTemplate;

  public TicketViewerClient(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Try<String> login(final Login login) {
    URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/login").build().toUri();
    Try<List<String>> maybeAuthorizations = Try.of(() ->
        restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(login), Object.class)
            .getHeaders().get("Authorization"));

    return maybeAuthorizations.map(auths -> auths.get(0));
  }

  public Try<Ticket> getTicket(final long ticketId, final String token) {
    URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/tickets/" + ticketId).build().toUri();

    return Try.of(() ->
        restTemplate.exchange(uri,
            HttpMethod.GET, getHttpEntity(token), Ticket.class).getBody());
  }

  public Try<Tickets> listTickets(final long pageNumber, final String token) {
    URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/tickets?page=" + pageNumber).build().toUri();

    return Try.of(() ->
        restTemplate.exchange(uri,
            HttpMethod.GET, getHttpEntity(token), Tickets.class).getBody());
  }

  private HttpEntity getHttpEntity(final String token) {
    return new HttpEntity<>(createHeaders(token));
  }

  private HttpHeaders createHeaders(final String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", token);
    headers.add("Accept", "application/json");

    return headers;
  }

}
