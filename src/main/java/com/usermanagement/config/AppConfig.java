package com.usermanagement.config;

import com.mongodb.MongoClient;
import com.usermanagement.imp.ConcreteCommand;
import com.usermanagement.model.User;
import com.usermanagement.utility.Constants;
import javafx.application.Application;
import org.apache.log4j.PropertyConfigurator;
import org.primefaces.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import java.io.File;

@Configuration
@ComponentScan(basePackages="com.usermanagement.model,com.usermanagement.imp")
@EnableWebMvc
public class AppConfig {

    @Autowired
    ServletContext servletContext;

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
        return new MongoClient(Constants.MongoDbHost);
    }

    @Bean
    MongoOperations mongoOperations(MongoClient mongoClient) throws Exception{
        return new MongoTemplate(mongoClient,Constants.MongoDbTable);
    }

    @Bean
    ConcreteCommand concreteCommand(MongoOperations mongoOperations){
        initializeLog4j();
        ConcreteCommand concreteCommand = new ConcreteCommand();
        concreteCommand.mongoOperation = mongoOperations;
        return concreteCommand;
    }

    private void initializeLog4j(){
        String log4jConfigFile = servletContext.getInitParameter(Constants.Log4JConfigLocation);
        String fullPath = servletContext.getRealPath("") + File.separator + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);

    }

}
