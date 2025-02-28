package it.muretti.micro.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration

public class MongoConfigRepository {
	
	

	    @Bean
	    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory) {
	        return new MongoTemplate(mongoDbFactory);
	    }
	}


