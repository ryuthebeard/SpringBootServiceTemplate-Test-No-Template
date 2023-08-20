package co.shara.springtemplate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.shara.springtemplate.kafka.ProducerService;
import co.shara.springtemplate.model.NotificationRequest;
import co.shara.springtemplate.model.NotificationResponse;
import co.shara.springtemplate.repo.SmsNotificationRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class NotificationServiceTest {


  @Mock
  private ProducerService producerService;

  @Mock
  private SmsNotificationRepo smsNotificationRepo;

  @InjectMocks
  private NotificationService notificationService;

  @Test
  void testNotify() throws JsonProcessingException {

    String UUID = java.util.UUID.randomUUID().toString();
    NotificationRequest notificationRequest = NotificationRequest.builder()
        .messageType("SMS")
        .messageId(UUID)
        .receiver("Test")
        .sender("test")
        .build();

    NotificationResponse notificationResponse = notificationService.notify(notificationRequest);

    assertNotNull(notificationResponse);
    assertEquals(1, notificationResponse.getCode());
  }
}