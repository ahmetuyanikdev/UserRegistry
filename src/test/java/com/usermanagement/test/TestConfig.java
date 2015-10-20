package com.usermanagement.test;

import com.mongodb.MongoClient;
import com.usermanagement.imp.ConcreteCommand;
import com.usermanagement.imp.Controller;
import com.usermanagement.model.User;
import com.usermanagement.utility.Constants;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * Created by ahmet on 19/10/15.
 */

@Configuration
public class TestConfig {

    ServletContext servletContext;

    TestConfig(){
        servletContext = Mockito.mock(ServletContext.class);
        //Mockito.when(servletContext.getInitParameter(Constants.Log4JConfigLocation)).thenReturn("WEB-INF/log4j.properties");
        //Mockito.when(servletContext.getRealPath("")).thenReturn("/Users/ahmet/Desktop/glassfish4/glassfish/domains/domain1/applications/UserManagement-1.0-SNAPSHOT");
    }

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
        return Mockito.mock(MongoTemplate.class);
    }

    @Bean
    ConcreteCommand concreteCommand(MongoOperations mongoOperations){
        ConcreteCommand concreteCommand = new ConcreteCommand();
        concreteCommand.mongoOperation = mongoOperations;
        return concreteCommand;
    }


    private void initializeLog4j(){
        String log4jConfigFile = servletContext.getInitParameter(Constants.Log4JConfigLocation);
        String fullPath = servletContext.getRealPath("") + File.separator + log4jConfigFile;
        PropertyConfigurator.configure(fullPath);

    }

    @Bean
    Controller controller(User user,ConcreteCommand concreteCommand){
        Controller controller = new Controller();
        controller.log = Mockito.mock(Logger.class);
        controller.setConcreteCommand(concreteCommand);
        controller.setUser(user);
        controller.setSelectedUser(user);

        return controller;
    }



}
