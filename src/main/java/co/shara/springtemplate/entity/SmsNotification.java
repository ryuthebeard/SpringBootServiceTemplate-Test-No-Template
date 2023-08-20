package co.shara.springtemplate.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Entity
@Table(name = "test_1")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsNotification implements Serializable {

  @Id
  @GeneratedValue(generator = "test_id_1_seq")
  @SequenceGenerator(name = "test_id_1_seq", sequenceName = "test_id_1_seq", allocationSize = 1)
  private long id;

  @Column(name = "message_id", unique = true)
  private String messageId;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "message")
  private String message;

  @Column(name = "sender")
  private String sender;

  @Column(name = "status")
  private String status;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false)
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;
}
