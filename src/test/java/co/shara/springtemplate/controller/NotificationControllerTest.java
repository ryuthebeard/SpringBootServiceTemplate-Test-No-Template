package co.shara.springtemplate.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import co.shara.springtemplate.model.NotificationRequest;
import co.shara.springtemplate.model.NotificationResponse;
import co.shara.springtemplate.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest
class NotificationControllerTest {


  @Autowired
  ObjectMapper mapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private NotificationService notificationService;

  @Test
  void testNotifyShouldReturnQueued() throws Exception {

    NotificationResponse notificationResponse = new NotificationResponse(1,
        "Message queued successfully");

    String UUID = java.util.UUID.randomUUID().toString();
    NotificationRequest notificationRequest = NotificationRequest.builder()
        .messageType("SMS")
        .messageId(UUID)
        .receiver("Test")
        .message("Test message")
        .sender("test")
        .build();

    when(notificationService.notify(any())).thenReturn(notificationResponse);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v4/notification")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(notificationRequest));

    this.mockMvc.perform(mockRequest).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.code", is(1)));
  }

  @Test
  void fieldsMissing() throws Exception {
    NotificationRequest request = NotificationRequest.builder().sender(null).build();

    mockMvc.perform(post("/api/v4/notification").contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }


  @Test
  void sendInvalidMessageId() throws Exception {
    NotificationRequest notificationRequest = NotificationRequest.builder()
        .messageType("SMS")
        .messageId("12345555")
        .receiver("Test")
        .sender("test")
        .build();

    mockMvc.perform(post("/api/v4/notification").contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(notificationRequest)))
        .andExpect(status().isBadRequest());
  }


  @Test
  void testNotifyDuplicateMessageId() throws Exception {

    String UUID = java.util.UUID.randomUUID().toString();
    NotificationRequest notificationRequest = NotificationRequest.builder()
        .messageType("SMS")
        .messageId(UUID)
        .receiver("Test")
        .message("Test message")
        .sender("test")
        .build();

    ConstraintViolationException constraintViolationException = new ConstraintViolationException("Duplicate", new HashSet<>());

    when(notificationService.notify(any())).thenThrow(constraintViolationException);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v4/notification")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(this.mapper.writeValueAsString(notificationRequest));

    this.mockMvc.perform(mockRequest).andDo(print()).andExpect(status().isBadRequest());
  }
}