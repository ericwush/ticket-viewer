package ericwush.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Custom ObjectMapper that supports specific features for JSON processing
 */
public class ZendeskObjectMapper extends ObjectMapper {

  public ZendeskObjectMapper() {
    this.registerModule(new JavaTimeModule());
    this.registerModule(new Jdk8Module());
  }

}
