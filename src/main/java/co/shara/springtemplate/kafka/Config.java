package co.shara.springtemplate.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

  @Value("${kafka.notification.topic.name}")
  private String topicName;

  @Value("${kafka.notification.partitions}")
  private int numPartitions;

  @Value("${kafka.notification.replicas}")
  private int replicas;

  @Bean
  NewTopic notificationTopic() {
    return new NewTopic(topicName, numPartitions, (short) replicas);
  }

}
