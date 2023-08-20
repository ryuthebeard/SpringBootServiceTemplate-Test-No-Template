package co.shara.springtemplate.service;


import co.shara.springtemplate.entity.SmsNotification;
import co.shara.springtemplate.kafka.ProducerService;
import co.shara.springtemplate.model.NotificationRequest;
import co.shara.springtemplate.model.NotificationResponse;
import co.shara.springtemplate.repo.SmsNotificationRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {


  private final ProducerService producerService;
  private final SmsNotificationRepo smsNotificationRepo;

  public NotificationResponse notify(NotificationRequest request) throws JsonProcessingException {
    producerService.sendMessage(request.getMessageId(), request);

    // Save request
    SmsNotification smsNotification = SmsNotification.builder()
        .messageId(request.getMessageId())
        .sender(request.getSender())
        .phoneNumber(request.getReceiver())
        .message(request.getMessage())
        .status("QUEUED")
        .build();

    smsNotificationRepo.save(smsNotification);

    log.info("Record created with id {}", smsNotification.getId());

    return new NotificationResponse(1, "Message queued successfully");
  }


  public Optional<SmsNotification> getById(long id) {
    return smsNotificationRepo.findById(id);
  }

}
