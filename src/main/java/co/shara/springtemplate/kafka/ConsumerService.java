package co.shara.springtemplate.kafka;


import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {


  private final CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(topics = "${kafka.notification.topic.name}",
      groupId = "${kafka.consumer.group-id}")
  public void consume(ConsumerRecord<String, String> consumerRecord) {
    log.info("### Key: {}", consumerRecord.key());
    log.info("### Headers: {}", consumerRecord.headers());
    log.info("### Value: {}", consumerRecord.value());
    latch.countDown();

  }


  public CountDownLatch getLatch() {
    return latch;
  }
}
