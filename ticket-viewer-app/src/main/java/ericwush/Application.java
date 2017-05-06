package ericwush;

import ericwush.integration.ZendeskObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    RestTemplate restTemplate = builder.build();
    restTemplate.getMessageConverters().stream()
        .filter(converter -> converter.getClass().equals(MappingJackson2HttpMessageConverter.class))
        .findFirst()
        .ifPresent(jsonConverter -> ((MappingJackson2HttpMessageConverter) jsonConverter).setObjectMapper(objectMapper()));
    return restTemplate;
  }

  @Bean
  public ZendeskObjectMapper objectMapper() {
    return new ZendeskObjectMapper();
  }
}
