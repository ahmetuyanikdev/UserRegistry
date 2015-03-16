/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 *
 * @author Hp
 */
@Configuration
@EnableMongoRepositories
public class SpringMongoConfig {
    
    public @Bean
	MongoTemplate mongoTemplate() throws Exception {
 
		MongoTemplate mongoTemplate = 
			new MongoTemplate(new MongoClient("localhost"),"UsersDB");
		return mongoTemplate;
	} 
}
