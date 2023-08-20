package co.shara.springtemplate.model;


import co.shara.springtemplate.controller.validations.Uuid;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationRequest {


  @NotNull
  @JsonProperty("message_type")
  private String messageType;
  @Uuid
  @JsonProperty("message_id")
  private String messageId;
  @NotNull
  private String receiver;
  @NotNull
  private String sender;
  @NotNull
  private String message;

}
