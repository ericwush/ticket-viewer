package ericwush.model.converter;

import ericwush.model.Ticket;
import ericwush.model.dto.ZendeskRequest;
import org.junit.Test;

import static ericwush.helper.TestHelper.assertTicket;
import static ericwush.helper.TestHelper.createZendeskRequest;

public class ZendeskRequestConverterTest {

  @Test
  public void shouldConvertRequest() {
    // Given
    ZendeskRequestConverter converter = new ZendeskRequestConverter();

    ZendeskRequest.Request request = createZendeskRequest();

    // When
    Ticket ticket = converter.apply(request);

    // Then
    assertTicket(ticket);
  }

}