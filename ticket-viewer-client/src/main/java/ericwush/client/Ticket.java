package ericwush.client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class Ticket {

  private Long id;
  private String subject;
  private String description;
  private String status;
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime updatedAt;

  public Ticket() {}

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

  @Override
  public String toString() {
    return "Ticket{" +
        "id=" + id +
        ", subject='" + subject + '\'' +
        ", description='" + description + '\'' +
        ", status='" + status + '\'' +
        ", updatedAt=" + updatedAt +
        '}';
  }

}
