package com.kh.eatsMap.common.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


@EnableMongoRepositories
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration{

	
	@Override
	protected String getDatabaseName() {
		return "mongoTest";
	}
	
 
	@Override
	public Collection getMappingBasePackages() {
	    return Collections.singleton("com.kh.mongo");
	}

//	@Override
//	protected void configureClientSettings(Builder builder) {
//	
//	  builder
//  	    .credential(MongoCredential.createCredential("yudi", "final", "yudi123".toCharArray()))
//  	    .applyToClusterSettings(settings  -> {
//  	    	settings.hosts(List.of(new ServerAddress("127.0.0.1", 27017)));
//	    });
//	}

	
}
