package co.shara.springtemplate.repo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import co.shara.springtemplate.entity.SmsNotification;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest(properties = {"spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect"})
@ExtendWith(MockitoExtension.class)
class SmsNotificationRepoTest {

  // test build with no postgres conn
  @Autowired
  SmsNotificationRepo smsNotificationRepo;

  // Generic methods like save...findById not necessary...commented out this because h2 is not creating sequence
  @Test
  void testSave() {

    SmsNotification smsNotification = SmsNotification.builder()
        .messageId("1")
        .sender("TEST SENDER")
        .phoneNumber("+254717746565")
        .message("test message")
        .status("QUEUED")
        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
        .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
        .build();

    SmsNotification savedSmsNotification = smsNotificationRepo.save(smsNotification);

    assertThat(savedSmsNotification).isNotNull();
    assertThat(savedSmsNotification.getId() > 0);
    assertEquals("1", savedSmsNotification.getMessageId());
    assertEquals(smsNotification.getSender(), savedSmsNotification.getSender());
    assertEquals(smsNotification.getPhoneNumber(), savedSmsNotification.getPhoneNumber());
    assertEquals(smsNotification.getMessage(), savedSmsNotification.getMessage());
    assertEquals(smsNotification.getStatus(), savedSmsNotification.getStatus());
  }
}