/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usermanagement.imp;

import com.usermanagement.intfc.Command;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
/** 
 *  
 * @author Ahmet Uyanik
 * This method is defined for User operations like,insert,delete,update
 */
public class ConcreteCommand implements Command{
    private MongoOperations mongoOperation;
    private ApplicationContext context;
    
    public ConcreteCommand(){
       
        context = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        mongoOperation=(MongoOperations)context.getBean("mongoTemplate");
         
    }
    
     
    @Override
    public void delete(Object obj) {
       mongoOperation.remove(obj);
    }

    
    @Override
    public void insert(Object obj) {
        mongoOperation.insert(obj);
    }
 
    
    @Override
    public void update(Object obj) {
        mongoOperation.save(obj);
    }

    
    @Override
    public List get(Class cls) {
        return mongoOperation.findAll(cls);
        
    }
    
}
