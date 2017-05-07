package ericwush.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Successful cases are tested in controller tests

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void shouldRejectGetTicketWhenUnauthenticated() throws Exception {
    mvc.perform(get("/api/tickets/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  public void shouldRejectGetTicketsWhenUnauthenticated() throws Exception {
    mvc.perform(get("/api/tickets?page=1").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

}
