package co.shara.springtemplate.repo;

import co.shara.springtemplate.entity.SmsNotification;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

public interface SmsNotificationRepo extends CrudRepository<SmsNotification, Long> {

  @CachePut(value = "notifications", key = "#smsNotification.id")
  SmsNotification save(SmsNotification smsNotification);

  @Cacheable(value = "notifications", key = "#id")
  Optional<SmsNotification> findById(Long id);

  @CacheEvict(value = "notifications", key = "#id")
  void delete(SmsNotification smsNotification);

}
