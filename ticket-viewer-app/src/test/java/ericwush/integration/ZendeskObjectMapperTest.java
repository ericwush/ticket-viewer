package ericwush.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;

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
    mapper.disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    LocalDateTime expected = LocalDateTime.of(2017, 5, 4, 22, 54, 7, 0);

    // When
    LocalDateTime deserializedDateTime = mapper.readValue("\"2017-05-04T22:54:07Z\"", LocalDateTime.class);

    // Then
    assertThat(deserializedDateTime, is(equalTo(expected)));
  }

  @Test
  public void shouldUnwrapRootValue() throws IOException {
    // Given
    TestObject expected = new TestObject();
    expected.setValue("123");

    // When
    TestObject actual = mapper.readValue("{\"test\":{\"value\":\"123\"}}", TestObject.class);

    // Then
    assertThat(actual, is(equalTo(expected)));
  }

}