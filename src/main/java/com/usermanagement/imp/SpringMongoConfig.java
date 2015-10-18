/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;

import com.mongodb.MongoClient;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
/**
 *
 * @author Ahmet Uyanik
 * 
 */
@Configuration
@EnableMongoRepositories
public class SpringMongoConfig {
    public @Bean  
	MongoTemplate mongoTemplate() throws Exception {
                //Context env = (Context) new InitialContext().lookup("java:comp/env");
                //String server = (String)env.lookup("Server");
                //String db = (String)env.lookup("DBName");
		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient("192.168.99.100:32774"),"test");
		return mongoTemplate;
	} 
}
