package co.shara.springtemplate.controller;

import co.shara.springtemplate.entity.SmsNotification;
import co.shara.springtemplate.model.NotificationRequest;
import co.shara.springtemplate.model.NotificationResponse;
import co.shara.springtemplate.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v4/notification")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;


  @GetMapping("/{id}")
  public ResponseEntity<Object> getNotificationById(@PathVariable Long id) {
    Optional<SmsNotification> optionalSmsNotification = notificationService.getById(id);
    if (optionalSmsNotification.isEmpty()) {
      throw new ValidationException("Record not found");
    }
    return new ResponseEntity<>(optionalSmsNotification.get(), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<Object> getNotifications() {
    return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> createNotification(@Valid @RequestBody NotificationRequest request)
      throws JsonProcessingException {

    NotificationResponse response = notificationService.notify(request);
    log.info("Response {}", response);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
