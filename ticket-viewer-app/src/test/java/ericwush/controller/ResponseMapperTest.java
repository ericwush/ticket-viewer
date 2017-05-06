package ericwush.controller;

import ericwush.model.Ticket;
import javaslang.control.Try;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ResponseMapperTest {

  private ResponseMapper mapper;

  @Before
  public void setUp() {
    mapper = new ResponseMapper();
  }

  @Test
  public void shouldMapOkResponse() {
    // Given
    Ticket ticket = new Ticket(100L, "subject", "description", "open", LocalDateTime.now());

    // When
    ResponseEntity response = mapper.apply(Try.success(ticket));

    // Then
    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    assertThat(response.getBody(), is(ticket));
  }

  @Test
  public void shouldMapResponseForHttpStatusCodeException() {
    // Given
    HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "RecordNotFound");

    // When
    ResponseEntity response = mapper.apply(Try.failure(exception));

    // Then
    assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    assertThat(response.getBody(), is("Zendesk 404 RecordNotFound"));
  }

  @Test
  public void shouldMapResponseForOtherException() {
    // Given
    IllegalStateException exception = new IllegalStateException("connection refused");

    // When
    ResponseEntity response = mapper.apply(Try.failure(exception));

    // Then
    assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    assertThat(response.getBody(), is("Zendesk connection refused"));
  }

}