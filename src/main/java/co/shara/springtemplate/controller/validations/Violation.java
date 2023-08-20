package co.shara.springtemplate.controller.validations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class Violation {

  private final String fieldName;
  private final String message;
}
