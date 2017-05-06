package ericwush.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class Ticket {

  private final Long id;
  private final String subject;
  private final String description;
  private final String status;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private final LocalDateTime updatedAt;

  public Ticket(final Long id, final String subject, final String description, final String status,
                final LocalDateTime updatedAt) {
    this.id = id;
    this.subject = subject;
    this.description = description;
    this.status = status;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public String getSubject() {
    return subject;
  }

  public String getDescription() {
    return description;
  }

  public String getStatus() {
    return status;
  }

}
