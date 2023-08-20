package co.shara.springtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringTemplateApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringTemplateApplication.class, args);
  }

}
