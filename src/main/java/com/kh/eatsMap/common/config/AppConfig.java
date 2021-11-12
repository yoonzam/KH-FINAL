package com.kh.eatsMap.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories
public class AppConfig {

  public @Bean MongoClient mongoClient() {
      return MongoClients.create("mongodb+srv://yudi:yudi123@mongotest.w6gmc.mongodb.net/final?retryWrites=true&w=majority");
  }

  public @Bean MongoTemplate mongoTemplate() {
      return new MongoTemplate(mongoClient(), "final");
  }
 
  
   
}
