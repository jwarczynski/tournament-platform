package pl.warczynski.jedrzej.backend;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.SecureRandom;
import java.util.Arrays;

@SpringBootApplication
public class BackendApplication implements WebMvcConfigurer {

  @Bean
  public SecureRandom secureRandom() {
    return new SecureRandom();
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS");
  }

//  @Bean
//  public MongoTemplate mongoTemplate() {
//    MongoClientSettings settings = MongoClientSettings.builder()
//            .applyToClusterSettings(builder ->
//                    builder.hosts(Arrays.asList(new ServerAddress("<cluster-address>", 27017))))
//            .credential(MongoCredential.createCredential("<username>", "<database>", "<password>".toCharArray()))
//            .build();
//
//    MongoClient mongoClient = MongoClients.create(settings);
//    return new MongoTemplate(mongoClient, "tournament-platform");
//  }


  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

}
