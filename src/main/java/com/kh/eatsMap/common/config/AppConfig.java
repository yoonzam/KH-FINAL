package com.kh.eatsMap.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Configuration
@EnableMongoRepositories(basePackages = "com.kh.eatsMap.*.model.repository")
public class AppConfig extends AbstractMongoClientConfiguration {

  public @Bean MongoClient mongoClient() {
      return MongoClients.create("mongodb+srv://won:won123@mongotest.w6gmc.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
  }

  public @Bean MongoTemplate mongoTemplate() {
      return new MongoTemplate(mongoClient(), "final");
  }

  @Override
  protected String getDatabaseName() {
	  return "final";
  }
  
}