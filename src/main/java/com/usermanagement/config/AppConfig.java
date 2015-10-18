package com.usermanagement.config;

import com.mongodb.MongoClient;
import com.usermanagement.imp.ConcreteCommand;
import com.usermanagement.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages="com.usermanagement.model,com.usermanagement.imp")
@EnableWebMvc
public class AppConfig {

    @Bean
    User user(){
        return new User();
    }

    @Bean
    User selectedUser(){
        return new User();
    }

    @Bean
    MongoClient mongoClient() throws Exception{
        return new MongoClient("192.168.99.100:32768");
    }

    @Bean
    MongoOperations mongoOperations(MongoClient mongoClient) throws Exception{
        return new MongoTemplate(mongoClient,"test");
    }

    @Bean
    ConcreteCommand concreteCommand(MongoOperations mongoOperations){
        ConcreteCommand concreteCommand = new ConcreteCommand();
        concreteCommand.mongoOperation = mongoOperations;
        return concreteCommand;
    }

}
