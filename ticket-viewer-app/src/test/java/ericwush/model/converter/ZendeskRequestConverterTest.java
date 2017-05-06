package ericwush.model.converter;

import ericwush.model.Ticket;
import ericwush.model.dto.ZendeskRequest;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ZendeskRequestConverterTest {

  @Test
  public void shouldConvertRequest() {
    // Given
    ZendeskRequestConverter converter = new ZendeskRequestConverter();

    ZendeskRequest request = new ZendeskRequest();
    ZendeskRequest.Request body = new ZendeskRequest.Request();
    body.setId(100L);
    body.setAssigneeId(1999L);
    body.setOrganizationId(2000L);
    body.setRequesterId(3000L);
    body.setDescription("description");
    body.setStatus("Open");
    body.setSubject("Can't login");
    body.setUrl("http://someurl");
    body.setCreatedAt(LocalDateTime.of(2017, 5, 1, 10, 00, 00));
    body.setUpdatedAt(LocalDateTime.of(2017, 5, 1, 11, 10, 00));
    request.setBody(body);

    // When
    Ticket ticket = converter.apply(request);

    // Then
    assertThat(ticket.getId(), is(100L));
    assertThat(ticket.getSubject(), is("Can't login"));
    assertThat(ticket.getDescription(), is("description"));
    assertThat(ticket.getStatus(), is("Open"));
    assertThat(ticket.getUpdatedAt(), is(LocalDateTime.of(2017, 5, 1, 11, 10, 0)));
  }

}