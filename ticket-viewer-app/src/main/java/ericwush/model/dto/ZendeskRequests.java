package ericwush.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ZendeskRequests {

  private ZendeskRequest.Request[] requests;
  private long count;
  @JsonProperty("next_page")
  private String nextPage;
  @JsonProperty("previous_page")
  private String previousPage;

  public ZendeskRequest.Request[] getRequests() {
    return requests;
  }

  public void setRequests(final ZendeskRequest.Request[] requests) {
    this.requests = requests;
  }

  public long getCount() {
    return count;
  }

  public void setCount(final long count) {
    this.count = count;
  }

  public String getNextPage() {
    return nextPage;
  }

  public void setNextPage(final String nextPage) {
    this.nextPage = nextPage;
  }

  public String getPreviousPage() {
    return previousPage;
  }

  public void setPreviousPage(final String previousPage) {
    this.previousPage = previousPage;
  }

}
