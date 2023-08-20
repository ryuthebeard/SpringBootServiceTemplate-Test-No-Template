package co.shara.springtemplate.kafka;

import co.shara.springtemplate.model.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {

  private final KafkaTemplate<String, String> kafkaTemplate;
  @Autowired
  ObjectMapper mapper;
  @Value("${kafka.notification.topic.name}")
  private String topicName;

  public String sendMessage(String key, NotificationRequest request)
      throws JsonProcessingException {
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, key,
        mapper.writeValueAsString(request));

    future.whenComplete((result, ex) -> {
      if (ex == null) {
        final RecordMetadata m = result.getRecordMetadata();
        log.info("Produced record to topic {} partition {} @ offset {}",
            m.topic(),
            m.partition(),
            m.offset());
      } else {
        log.info("Error occurred");
      }
    });

    return "Success";
  }
}
