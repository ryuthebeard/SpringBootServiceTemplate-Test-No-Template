package co.shara.springtemplate.kafka;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import co.shara.springtemplate.model.NotificationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;


//@SpringBootTest
//@DirtiesContext
//@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://127.0.0.1:9092","port=9092"})
@Slf4j
class ConsumerServiceITTest {

  @Autowired
  ProducerService producerService;

  @Autowired
  ConsumerService consumerService;

  @Autowired
  private EmbeddedKafkaBroker embeddedKafka;

  //@Test
  void testConsumeFromTopic() throws JsonProcessingException, InterruptedException {

    NotificationRequest request = NotificationRequest.builder()
        .message("test message")
        .build();

    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("notif-consumers", "true",
        this.embeddedKafka);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    ConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);

    // I don't understand what is happening here
    Consumer<Integer, String> consumer = cf.createConsumer();
    this.embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "notification");

    producerService.sendMessage("test", request);

    boolean messageConsumed = consumerService.getLatch().await(10, TimeUnit.SECONDS);

    assertTrue(messageConsumed);
  }
}