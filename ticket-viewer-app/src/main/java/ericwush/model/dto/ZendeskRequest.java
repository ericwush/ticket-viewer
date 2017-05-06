package ericwush.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZendeskRequest {

  @JsonProperty("request")
  private Request body;

  public Request getBody() {
    return body;
  }

  public void setBody(final Request body) {
    this.body = body;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Request {
    private Long id;
    private String url;
    private String status;
    private String subject;
    private String description;
    @JsonProperty("organization_id")
    private Long organizationId;
    @JsonProperty("requester_id")
    private Long requesterId;
    @JsonProperty("assignee_id")
    private Long assigneeId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
      return id;
    }

    public void setId(final Long id) {
      this.id = id;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(final String url) {
      this.url = url;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(final String status) {
      this.status = status;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(final String subject) {
      this.subject = subject;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(final String description) {
      this.description = description;
    }

    public LocalDateTime getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
      this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
    }

    public Long getOrganizationId() {
      return organizationId;
    }

    public void setOrganizationId(final Long organizationId) {
      this.organizationId = organizationId;
    }

    public Long getRequesterId() {
      return requesterId;
    }

    public void setRequesterId(final Long requesterId) {
      this.requesterId = requesterId;
    }

    public Long getAssigneeId() {
      return assigneeId;
    }

    public void setAssigneeId(final Long assigneeId) {
      this.assigneeId = assigneeId;
    }
  }

}
