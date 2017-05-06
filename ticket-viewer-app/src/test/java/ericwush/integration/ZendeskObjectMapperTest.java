package ericwush.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ZendeskObjectMapperTest {

  private ZendeskObjectMapper mapper;

  @Before
  public void setUp() {
    mapper = new ZendeskObjectMapper();
  }

  @Test
  public void shouldDeserializeLocalDateTime() throws IOException {
    // Given
    LocalDateTime expected = LocalDateTime.of(2017, 5, 4, 22, 54, 7, 0);

    // When
    LocalDateTime deserializedDateTime = mapper.readValue("\"2017-05-04T22:54:07Z\"", LocalDateTime.class);

    // Then
    assertThat(deserializedDateTime, is(equalTo(expected)));
  }

  @Test
  public void shouldSerializeValueWhenOptionalPresent() throws JsonProcessingException {
    // Given

    // When
    String json = mapper.writeValueAsString(Optional.of("ABC"));

    // Then
    assertThat(json, is(equalTo("\"ABC\"")));
  }

  @Test
  public void shouldSerializeNullWhenOptionalPresent() throws JsonProcessingException {
    // Given

    // When
    String json = mapper.writeValueAsString(Optional.empty());

    // Then
    assertThat(json, is(equalTo("null")));
  }

}