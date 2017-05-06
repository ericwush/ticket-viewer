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
    private long id;
    private String url;
    private String status;
    private String subject;
    private String description;
    @JsonProperty("organization_id")
    private long organizationId;
    @JsonProperty("requester_id")
    private long requesterId;
    @JsonProperty("assignee_id")
    private long assigneeId;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public long getId() {
      return id;
    }

    public void setId(final long id) {
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

    public long getOrganizationId() {
      return organizationId;
    }

    public void setOrganizationId(final long organizationId) {
      this.organizationId = organizationId;
    }

    public long getRequesterId() {
      return requesterId;
    }

    public void setRequesterId(final long requesterId) {
      this.requesterId = requesterId;
    }

    public long getAssigneeId() {
      return assigneeId;
    }

    public void setAssigneeId(final long assigneeId) {
      this.assigneeId = assigneeId;
    }
  }

}
